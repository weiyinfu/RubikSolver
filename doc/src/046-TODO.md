实现网页版，根据几张图片推导推导魔方初始状态，从而给出解法。难点：颜色等价（不同魔方可能颜色不太一样，不能把颜色写死，而要通过聚类的方式得到六种颜色）。视觉模块分为识别每个小面、识别大面之间的相对位置、转化为剑形输入三个模块。

# 暴力解决三阶魔方的12个边块
三阶魔方的八个角块可以像二阶一样去解决，只需要找到不改变角块时边块的操作方法，就能够找到一种解决三阶魔方的方法。  
fac(12)*4096
1961990553600=1827G