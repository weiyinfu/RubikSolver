package cn.weiyinfu.rubik.diamond.finder;

import cn.weiyinfu.rubik.diamond.V3;

import java.util.*;

import static cn.weiyinfu.rubik.diamond.Linalg.distance;
import static cn.weiyinfu.rubik.diamond.Linalg.mean;

/**
 * 给定一个四面体，把它划分成n份，求各个小四面体的中心坐标
 */
public abstract class Skeleton {
    //根据skeleton生成各种操作之后的置换，免去繁琐的书写流程
    //四面体魔方的skeleton，记录若干个点
    public class FacePoint {
        public int pointIndex;
        public int[] faceId;

        FacePoint(int pointIndex, int faceCount) {
            this.pointIndex = pointIndex;
            faceId = new int[faceCount];
            Arrays.fill(faceId, -1);
        }

        public int count() {
            int s = 0;
            for (int j : faceId) {
                if (j != -1) {
                    s++;
                }
            }
            return s;
        }
    }

    class Face {
        List<V3> centers;
        V3 heightVec;

        Face(List<V3> centers, V3 v) {
            this.centers = centers;
            this.heightVec = v;
        }
    }

    List<V3> a = new ArrayList<>();//骨骼点列表
    public Map<Integer, FacePoint> facePoints = new HashMap<>();

    //给定一个点寻找下标
    int findIndex(V3 p) {
        for (int i = 0; i < a.size(); i++) {
            if (distance(p, a.get(i)) < 1e-2) {
                return i;
            }
        }
        return -1;
    }

    // 设置面的映射，大面由ABC三点所决定
    // reverseTriangle表示是否反转顺序进行标注ID
    void setFaceMap(List<V3> faceCenters, V3 heightVec, int faceIndex, int faceCount) {
        int base = faceCenters.size() * faceIndex;
        for (int i = 0; i < faceCenters.size(); i++) {
            var faceCenter = mean(faceCenters.get(i));
            var bodyCenter = faceCenter.add(heightVec);
            var ind = findIndex(bodyCenter);
            if (ind == -1) {
                //没找到则添加
                ind = this.a.size();
                this.a.add(bodyCenter);
                var x = new FacePoint(ind, faceCount);
                this.facePoints.put(ind, x);
            }
            var x = this.facePoints.get(ind);
            x.faceId[faceIndex] = base + i;
        }
    }

    abstract List<Face> getFaces();

    public void init() {
        var faces = getFaces();
        for (int i = 0; i < faces.size(); i++) {
            var f = faces.get(i);
            setFaceMap(f.centers, f.heightVec, i, faces.size());
        }
    }

    public int[][] getSword() {
        var x = new ArrayList<>(facePoints.values());
        x.sort(Comparator.comparing(i -> i.pointIndex));
        int[][] sword = new int[x.size()][];
        for (int i = 0; i < sword.length; i++) {
            sword[i] = Arrays.stream(x.get(i).faceId).filter(j -> j != -1).toArray();
        }
        return sword;
    }
}
