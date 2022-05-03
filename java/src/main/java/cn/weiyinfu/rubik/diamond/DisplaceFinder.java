package cn.weiyinfu.rubik.diamond;

import java.util.*;

import static cn.weiyinfu.rubik.diamond.Linalg.*;
import static java.lang.Math.PI;

public class DisplaceFinder {
    TwelveState stateManager = new TwelveState();
    Skeleton skeleton;

    class Node {
        V3 position;
        int state;
        int pointIndex;

        Node(int state, int pointIndex) {
            this.state = state;
            this.pointIndex = pointIndex;
        }

        public Node copy() {
            Node no = new Node(this.state, this.pointIndex);
            no.position = this.position.copy();
            return no;
        }
    }

    public class Diamond {
        List<Node> a = new ArrayList<>();

        public Diamond rotate(int face, int layer, boolean turnTop) {
            //绕着哪个轴转，转几层，转尖还是转面，左手定则绕面旋转
            V3 f, t;
            V3[] faces;
            if (face == 0) {
                faces = new V3[]{skeleton.top, skeleton.left, skeleton.back};
                t = skeleton.right;
            } else if (face == 1) {
                faces = new V3[]{skeleton.top, skeleton.left, skeleton.right};
                t = skeleton.back;
            } else if (face == 2) {
                faces = new V3[]{skeleton.top, skeleton.right, skeleton.back};
                t = skeleton.left;
            } else {
                faces = new V3[]{skeleton.left, skeleton.right, skeleton.back};
                t = skeleton.top;
            }
            f = mean(faces);
            Diamond it = this.copy();
            for (int i = 0; i < this.a.size(); i++) {
                Node src = this.a.get(i);
                Node des = it.a.get(i);
                var dist = distanceToFace(faces[0], faces[1], faces[2], src.position);
                var turn = false;
                if (turnTop) {
                    if (dist > (skeleton.n - layer) * skeleton.smallDiamondHeight) {
                        turn = true;
                    }
                } else {
                    if (dist < layer * skeleton.smallDiamondHeight) {
                        turn = true;
                    }
                }
                if (turn) {
//                    System.out.printf("i=%s,dis=%s,layer=%s\n", i, distanceToFace(faces[0], faces[1], faces[2], src.position) / skeleton.smallDiamondHeight, layer);
                    des.position = turnByAxis(f, t, src.position, PI * 2 / 3.0);
                    des.state = stateManager.stateTable[src.state][face];
//                    System.out.println("====");
                }
            }
            return it;
        }

        public int[] getSkeleton() {
            int[] ans = new int[skeleton.totalFace];
            for (Node node : a) {
                int position = skeleton.findIndex(node.position);
                var faces = skeleton.facePoints.get(position);
                for (int i = 0; i < faces.faceId.length; i++) {
                    if (faces.faceId[i] != -1) {
                        ans[faces.faceId[i]] = node.pointIndex;
                    }
                }
            }
            return ans;
        }


        public int[] getFaces() {
            //渲染到面
            int[] ans = new int[skeleton.totalFace];
            for (Node i : this.a) {
                var positionIndex = skeleton.findIndex(i.position);
                //找到位于该位置的面映射
                //facePoint表示处于该位置的时候应该展示什么
                var facePoint = skeleton.facePoints.get(positionIndex);
                //original表示有什么可供展示的
                var originalFacePoint = skeleton.facePoints.get(i.pointIndex);
                //找到当前小块的状态
                var state = stateManager.nodes[i.state];
                //寻找应该展示什么
                for (int j = 0; j < facePoint.faceId.length; j++) {
                    if (facePoint.faceId[j] != -1) {
                        ans[facePoint.faceId[j]] = originalFacePoint.faceId[state.a[j]];
                    }
                }
            }
            return ans;
        }

        Diamond copy() {
            var it = new Diamond();
            for (int i = 0; i < this.a.size(); i++) {
                var no = this.a.get(i);
                it.a.add(no.copy());
            }
            return it;
        }

        @Override
        public String toString() {
            StringBuilder builder = new StringBuilder();
            builder.append("Diamond\n");
            builder.append("faces:" + Arrays.toString(getFaces()) + "\n");
            builder.append("skeleton:" + Arrays.toString(getSkeleton()) + "\n");
            builder.append("state:");
            for (var i : this.a) {
                var x = stateManager.nodes[i.state];
                builder.append(x.tos());
            }
            builder.append('\n');
            builder.append("colors:");
            for (var i : this.a) {
                var pointIndex = skeleton.findIndex(i.position);
                builder.append(Arrays.toString(skeleton.facePoints.get(pointIndex).faceId));
            }
            builder.append("\n");
            return builder.toString();
        }
    }


    public Diamond NewStartNode() {
        var d = new Diamond();
        for (int i = 0; i < skeleton.a.size(); i++) {
            var no = new Node(0, i);
            no.position = skeleton.a.get(i).copy();
            d.a.add(no);
        }
        return d;
    }

    int[] down(int layerCount) {
        //获取旋转下面layerCount层的置换
        var x = NewStartNode();
        return x.rotate(3, layerCount, false).getFaces();
    }

    public int[] left(int layerCount) {
        //获取旋转左面layerCount层的置换
        var x = NewStartNode();
        var displace = x.rotate(2, layerCount, true)
                .getFaces();
        return displacePower(displace, 2);
    }

    int[] right(int layerCount) {
        //获取旋转右面layerCount层的置换，绕着右面那个角旋转其实就是绕着左边那个面旋转
        var x = NewStartNode()
                .rotate(0, layerCount, true)
                .getFaces();
        return displacePower(x, 2);
    }

    int[] back(int layerCount) {
        //获取旋转后面layerCount层的置换，绕着后面那个角旋转其实就是绕着正面旋转
        var x = NewStartNode()
                .rotate(1, layerCount, true)
                .getFaces();
        return displacePower(x, 2);
    }

    public Map<String, int[]> getOperations() {
        Map<String, int[]> operations = new HashMap<>();

        for (int i = 1; i < skeleton.n; i++) {
            operations.put(String.format("左%s", i), left(i));
            operations.put(String.format("右%s", i), right(i));
            operations.put(String.format("后%s", i), back(i));
            operations.put(String.format("下%s", i), down(i));
        }
        return operations;
    }

    public Map<String, int[]> getOperationsWithoutCorner() {
        Map<String, int[]> operations = new HashMap<>();

        for (int i = 2; i < skeleton.n; i++) {
            operations.put(String.format("左%s", i), left(i));
            operations.put(String.format("右%s", i), right(i));
            operations.put(String.format("后%s", i), back(i));
            operations.put(String.format("下%s", i), down(i));
        }
        return operations;
    }

    public DisplaceFinder(int n) {
        this.skeleton = new Skeleton(n);
    }
}
