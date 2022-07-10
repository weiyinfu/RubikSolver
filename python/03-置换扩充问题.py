import math
from tqdm import tqdm
from collections import Counter
from itertools import permutations
from scipy.special import factorial
import numpy as np
"""
给定一个初始置换集合，求扩充之后得到的最终置换集的大小。
"""

def lcm(a):
    return math.lcm(*a)


def mul(a, b):
    c = np.empty_like(a)
    for i in range(len(a)):
        c[i] = a[b[i]]
    return c


def inverse(a):
    # 逆置换
    b = np.empty_like(a)
    for i in range(len(a)):
        b[a[i]] = i
    return b


show_detail = False


def brute_list(a):
    ma = set()
    q = [np.arange(len(a[0]))]
    while q:
        now = q.pop()
        for i in a:
            nex = mul(now, i)
            nex_key = tuple(nex)
            if nex_key in ma:
                continue
            ma.add(nex_key)
            q.append(nex)
    if show_detail:
        print(ma)
    return len(ma)


def brute(a):
    # 求给定置换能扩展到多少个
    return brute_list([a])


class FindMerge:
    def __init__(self, n):
        self.a = np.arange(n)

    def find(self, x):
        if self.a[x] == x:
            return x
        f = self.find(self.a[x])
        self.a[x] = f
        return f

    def merge(self, x, y):
        fx = self.find(x)
        fy = self.find(y)
        if fx == fy:
            return
        self.a[fx] = fy

    def compact(self):
        for i in range(len(self.a)):
            self.a[i] = self.find(i)


def polya(a):
    # 使用polya定理求置换能够扩展到多少个，也就是根据置换求等价类的个数
    vis = np.zeros_like(a, dtype=np.bool_)
    fm = FindMerge(len(a))

    def go(x):
        vis[x] = True
        fm.merge(x, a[x])
        if not vis[a[x]]:
            go(a[x])

    for i in range(len(a)):
        if vis[i]:
            continue
        go(i)
    fm.compact()
    v = list(Counter(fm.a).values())
    if show_detail:
        print("polya detail", v)
    return lcm(v)


def polya_list(a):
    # 使用polya定理求置换能够扩展到多少个，也就是根据置换求等价类的个数
    fm = FindMerge(len(a[0]))

    def handle_one(a):
        vis = np.zeros_like(a, dtype=np.bool_)

        def go(x):
            vis[x] = True
            fm.merge(x, a[x])
            if not vis[a[x]]:
                go(a[x])

        for i in range(len(a)):
            if vis[i]:
                continue
            go(i)

    for i in a:
        handle_one(i)
    fm.compact()
    v = list(Counter(fm.a).values())
    if show_detail:
        print("polya detail", v)
    return lcm(v)


def test_one():
    global show_detail
    show_detail = True
    a = (1, 0, 3, 4, 2)
    print(brute(a))
    print(polya(a))


def perm_test():
    for i in permutations(np.arange(5)):
        print(i)


def inverse_test():
    a = [1, 2, 0]
    print(inverse(a))
    print(mul(a, inverse(a)))


def one_permutation():
    n = 9
    for i in tqdm(permutations(np.arange(n)), total=factorial(n, True)):
        b = brute(i)
        p = polya(i)
        if b != p:
            print(i, b, p)


def get_two_op(n):
    a = list(permutations(np.arange(n)))
    for i in range(len(a)):
        for j in range(i + 1, len(a)):
            yield a[i], a[j]


def main():
    # not ready yet
    for ops in get_two_op(3):
        b = brute_list(ops)
        p = polya_list(ops)
        if b != p:
            print(ops, b, p)


main()
#
# test_one()
