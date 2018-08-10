package haha;

//convert 24 color to [8][2]
public class Converter {
// the color index of 8 position
int sword[][] = {{18, 6, 20}, {7, 10, 16}, {19, 21, 15}, {11, 14, 17}, {0, 4, 22}, {2, 5, 8},
        {1, 13, 23}, {3, 9, 12}};
// rywgob
int value[] = {1, 5, 9, -5, 0, -3};
// if the red is at face i,what's the state of this cube
int faceState[] = {2, 1, 0, 1, 2, 0};
// the input six faces
int in[][] = new int[6][4];
// 0 is position,1 is state
// ans[0][i] means the ith cube's position is at ans[0][i]
public int a[][] = new int[2][8];
int operation = 0;

int charToInt(char c) {
    int x = "rywgob".indexOf(c);
    if (x == -1)
        x = "RYWGOB".indexOf(c);
    if (x == -1)
        x = "红黄白绿橙蓝".indexOf(c);
    if (x == -1) {
        System.err.println("no this color " + c);
        System.exit(0);
    }
    return value[x];
}

void turn() {
    while (operation > 1) {
        int o = operation & 1;
        operation >>= 1;
        for (int i = 0; i < 8; i++) {
            a[1][i] = (5 - o - a[1][i]) % 3;
            int o1 = (o + 1) % 3, o2 = (o + 2) % 3;
            int p = a[0][i];
            a[0][i] = p & (1 << o1) | ((p >> o2) & 1) << o | (1 - ((p >> o) & 1)) << o2;
        }
    }
}

void map() {
    for (int i = 0; i < 8; i++) {
        int id = 0;
        int state = 0;
        for (int j = 0; j < 3; j++) {
            int face = sword[i][j] / 4;
            int c = in[face][sword[i][j] % 4];
            id += c;
            if (c == 0 || c == 1) {// red or orange
                state = faceState[face];
            }
        }
        a[0][id] = i;
        a[1][id] = state;
        if (id == 7) {// red blue white cube
            int redFace = 0, blueFace = 0;
            for (int j = 0; j < 3; j++) {
                int face = sword[i][j] / 4;
                int c = in[face][sword[i][j] % 4];
                if (c == -3) {
                    blueFace = face;
                } else if (c == 1) {
                    redFace = face;
                }
            }
            DownAndRight rightAndDown = new DownAndRight();
            operation = rightAndDown.ans[rightAndDown.state(redFace, blueFace)];
        }
    }
}

void init(String colors) {
    for (int i = 0; i < 6; i++) {
        for (int j = 0; j < 4; j++) {
            in[i][j] = charToInt(colors.charAt(i * 4 + j));
        }
    }
}

public Converter(String colors) {
    init(colors);
    map();
    turn();
}
}
