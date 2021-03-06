LinuxARM驱动编程
第1天、ARM体系结构和汇编
	ARM寄存器、工作模式
	汇编，要写出一个冒泡排序
第2天、ARM外设硬件裸机驱动
	时钟
	内存
	flash //就是硬盘
	串口
	GPIO
	看芯片手册（英文）
	看电路模型
第3天、ARM Bootloader
	相当于PC机中的BIOS
	学习时分为Boot和Loader两部分
		Boot：把一个开发板上的相关最小硬件设备驱动起来
		Loader：把硬盘（flash）上的操作系统（Linux/Android）加载到内存，然后去执行操作系统
		U-Boot：tiny210开发板（友善之臂）选用的处理器核心cortexA8（1GHz）
第4天、ARM中断编程
	软件中断
	硬件中断
第5天、Linux kernel核心数据结构分析
	VFS虚拟文件系统
		file
		kobject
	数据结构
		双向链表
		红黑树
		队列
第5天、Linux驱动简介
	字符设备（重点） led灯字符设备驱动 PC机上模拟字符设备驱动编程
	块设备
	网络设备
	USB设备
第6天、Linux中断（重点）
	上半部
	下半部
第7天、Linux串口驱动
	Linux中断编程
	字符设备设计

ARM是一个CPU内核，是一家CPU设计公司，是一种处理器架构的代表
ARM公司自己并不生产或销售芯片，它采用技术授权模式，通过出售芯片技术授权，收取授权费和技术转让费，ARM会把自己的技术出售给半导体公司，主要有三星 TI 飞利浦 飞思卡尔 华为海思 联发科

SOC称为片上系统，这些半导体公司拿到arm的技术之后在此基础上添加外设，例如三星公司在arm设计出来的CPU内核的基础上集成了LCD 2d/3d硬件加速 中断控制器 内存管理单元等外设之后，最终形成了一个CPU

ARM公司会给CPU内核起名字，例如ARM9、CORTEX A8等
三星公司将一些外设附加到CPU内核中后也会起一个名字，例如S5PV210
广州友善之臂公司在自己的开发板上使用三星的CPU，又给开发板起了一个名字，例如：Tiny210

目前几乎所有的智能设备用的都是arm的核心

基于ARM内核的处理器是目前消费类电子市场中占有量第一的处理器，尤其是手机和平板电脑行业

ARM是Advanced RISC Machine的缩写，最早的ARM处理器诞生于80年代的英国，目前总部在英国剑桥

每个厂商得到的ARM公司都是一套独一无二的ARM相关技术服务。利用这种合作关系，ARM很快成为许多全球性RISC标准的缔造者。
目前，共有30家半导体公司与ARM签订了硬件技术使用许可协议，其中包括Intel IBM L6半导体 NEC SONY 飞利浦和国家半导体这样的大公司。至于软件系统的合伙人，则包括微软 SUN和MRI等一系列知名公司

2006年arm芯片出货量为20亿片
2010年arm芯片出货量为60亿片
	
RISC 精简指令集Reduced Instruction Set Computing ARM/MIPS
CISC 复杂指令集Complex Instruction Set Computing x86

RISC指令长度是固定的，iPhone5s的arm指令集长度是64位，之前一直都是32位
CISC指令长度不是固定的，每次取指执行之前要判断指令的长度

相应的RISC指令能做的事情比CISC少，例如将内存中某块空间的数据复制到另一块内存空间中，RISC需要先将内存中的数据放到CPU中，再从CPU中取出放入内存，而CISC可以不通过CPU中转直接在内存空间之间复制

RISC的指令条数只有100多条汇编指令，有80%的指令较为常用
CISC的指令条数有3亿多条(1984年统计)，仅有20%的指令较为常用

上世纪70年代提出将复杂的CPU精简化，来做民用

mp3 mp4里面的芯片就是arm芯片

arm再往后发展就用于智能手机 平板电脑

CPU结构
——————————————
| 控   ALU运算单元         |
| 制                      |
| 器   寄存器             |
| 译                      |
| 码   预取器             |
| 器                      |
——————————————

ARM指令集的发展
ARM核心			体系结构
ARM1			V1
ARM2			V2
ARM2As，ARM3	V2a
.				.
.				.
.				.

V1 V2指的是汇编指令集
CPU硬件逻辑功能实现先于指令出现

ARM3可以跑由V1指令集写的程序
V2a指令集写的程序不能在ARM1上跑

ARM核心架构
ARM的共有37个32位寄存器，见图
7种工作模式
用户模式(User):ARM处理器正常的程序执行状态
快速中断模式(FIQ):用于高速数据传输或通道处理
外部中断模式(IRQ):用于通用的中断处理
管理模式(Supervisor):操作系统使用的保护模式
数据访问终止模式(Abort):当数据或指令预取终止时进入该模式,可用于虚拟存储及存储保护
系统模式(System):运行具有特权的操作系统任务
未定义指令中止模式(Undifined):当未定义的指令执行时进入该模式,可用于支持硬件协处理器的软件仿真

在同一时刻CPU只能工作在一种模式下

每种模式下：
R0-R12称为未分组寄存器或通用寄存器，用来存放临时变量
R13/SR:栈指针寄存器，记录栈顶地址
R14/LR:链接返回寄存器，保存程序的返回地址，如函数调用返回
R15/PC:程序计数器，PC指向哪个地址，CPU就到哪个地址取指令运行
CPSR:当前程序状态寄存器，保存程序的运行状态，即CPU的工作状态

X86下
RAIN0：最高权限	kernel
RAIN3：最低权限	user
具体看视频教程中的图

每个应用程序在内存中的物理空间都可以通过mmu映射到对应的虚拟内存空间
一个Linux kernel对应多个进程
kernel所在的3G-4G的那块内存只有RAIN0模式才能访问

如果普通的C语言程序试图操纵kernel时会报段错误

软终端，陷阱门：CPU从RAIN3转换到RAIN0，跳到了一个指定的地址执行

ARM下没有RAIN0 RAIN3之分，但是有两个级别七种模式
两个级别：非特权级 特权级对应x86模式下的RAIN3和RAIN0
USER模式是非特权模式，剩下的模式(普通中断模式、快速中断、系统模式、管理模式、abt终止模式、未定义指令模式)都是特权模式

内核kernel工作在管理模式下

CPSR：
31 30 29 28 27 26             7 6 5 4  3  2  1  0
N  Z  C  V  .  .  .  .  .  .  I F T M4 M3 M2 M1 M0
0-7 Control Bits
	M0-M4:Mode bits //标明处于哪种模式
		M[4:0]	Mode		Visible Thumb state registers
		10000	User
		10001	FIQ
		10010	IRQ
		10011	Supervisor
		10111	Abort
		11011	Undefined
		11111	System
	T:State bits //正常情况下内存中4个字节代表一条指令 有些时候需要取2个字节，这一位的0 1决定了在内存中取几个字节
	F:FIQ disable //快速中断
	I:IRQ disable //普通中断
	I和F都是中断开关位，后面介绍
8-27 Reserved
28-31 Condition Code Flags
	C:保存进位
	V:保存溢出位 两个数相加，如果最高位的进位和次高位的进位不同，则产生溢出
		这里要考虑符号位
		CPU仅仅是把数字相加起来，CPU不管正负数
		1000 + 1100 相加之后最高位有进位，进位1就保存在了C标志位，此时最高位进位是1，次高位进位是0，因此此时最高位和次高位进位不同，就产生了溢出，一旦溢出，V位就变成了1
	Z:0标志位，两个数相加结果为0时，Z标志位就成了1，该位用于if判断
	N:符号位，标识运算结果是正数还是负数，也就是存储运算结果的最高位
	
	NZCV这4位就是用来描述两个数相加运算结果的位

SPSR 备份程序状态寄存器 是用来备份CPSR的

每种模式下最多能看到18个寄存器，其中System和User模式下只有17个寄存器
这样一来，寄存器的个数就是7 * 18 - 2 = 124个
而我们之前说过总共有37个，这就涉及到了寄存器的共用了
首先任何模式下r0-r7这8个寄存器都可以共用
r8-r12在FIQ模式下是独立的，其余模式下的r8-r12是共用的，图中有三角的寄存器代表独立寄存器，跟别的不共用
r13-r14在每个模式下都有自己的r13-r14
r15共用
CPSR共用
SPSR 5种特权模式下分别独立
综上，37个寄存器分别为
r0      r1      r2       r3       r4     r5     r6     r7
r8      r9      r10      r11      r12
r8_fiq  r9_fiq  r10_fiq  r11_fiq  r12_fiq
r13     r14
r13_fiq r14_fiq
r13_svc r14_svc
r13_abt r14_abt
r13_irq r14_irq
r13_und r14_und
r15(PC)
CPSR
SPSR_fiq
SPSR_svc
SPSR_abt
SPSR_irq
SPSR_und

普通的应用程序，例如QQ都是工作在User模式下，当涉及到系统调用或进程间切换时会切换到Supervisor模式下，当突然来电话的时候就会进入IRQ模式下，当往手机里面拷贝东西时会进入FIQ模式，程序出现bug时就会进入Abort模式，当CPU取到了非法的指令时会进入undefined模式

小端存储：高地址存高字节，低地址存低字节 高存高，低存低
大端存储：高地址存低字节，低地址存高字节
网络字节序用的是大端存储

处理器组成原理
预取
译码
执行

流水线：说的是CPU内可以处理几条指令
处理器 流水线
ARM7  3级
ARM9  5级
ARM11 7级
Cortex A8 8级

ARM汇编分为：ARM指令(32位定长)，Thumb指令(16位定长)
Thumb指令为简化版的ARM指令，本文重点讲解ARM指令

汇编指令构造格式
31																0
cond(条件码 4bit) opt(操作码) 分类码 s(1bit) Rd(目的寄存器4bit) Rn(源寄存器4bit) 立即数(8bit)
如：MOV R0 #0x1 //把0x1这个数字放到R0寄存器中，立即数0x1前面必须加#号
	MOVS R0,R1
MOV就叫操作码
R0就是目的寄存器

add r0,r1,#0x55 //将r1+0x55赋给r0

s和cond是整个汇编的难点所在
当操作码add mov后面跟了s的时候s位就变成了1，s为1代表当前指令运算结果会影响或修改CPSR寄存器的相关标识位(NZCV)

if(a==b){
	aaa
}else{
	bbb
}
转换成汇编后就成了
mov r1,a
mov r2,b
subs r0,r1,r2 //CPSR中C标志位置为1
beq aaa //b代表跳转 CPSR中Z为1时，这条指令执行，否则这就是一条空指令
否则 bbb代码执行

指令执行前判断条件码
指令执行后根据S码的设置影响CPSR相关标志位

常用汇编指令
MOV
MVN
ADD
ADC
SUB
SBC
AND
ORR
EOR
CMP
BIC
LDR load  内存->寄存器
STR store 寄存器->内存
LDMFD
STMFD
B
BL
SWP
MRS
MSR


	area asm1test,code,readonly ;code代表放到代码段，readonly只读
	entry ;入口
	code32 ;32位指令，可以不写，默认为32位
aaa ;标号，代表下面这条指令的地址
	mov r0, #0x1 ;除了这行都是伪指令，伪指令都是给编译器看的
	mov r1, r0
	mov r2, #0x2
	
	add r3, r1, r2 ;r1 + r2 => r3
	sub r4, r3, r1 ;r3 - r1 => r4
	subs r0, r0, r0 ;sub后面加了s代表当前指令运算结果会影响或修改CPSR寄存器的相关标识位(NZCV) 该指令执行后cpsr中标志位变为nZCv(ZC两位变成了大写，代表这两位里面是1)，关于Z位变为1很好理解，两个数相加结果为0时，Z标志位就成了1，关于C位变为1是因为在减法操作中没有产生借位时C位就会被置为1，产生借位时C位会被置为0，这个行为和加法正好相逆
	
	b . ;b表示跳转 b后面的点表示当前指令地址，b .就是跳转到当前指令，在此是为了防止CPU跑飞
	
	mvn r5, #0x0 ;数据取反存储，即将0x0取反存到r5寄存器中，r5寄存器中就变成了ffffffff
	
	adc ;带进位的加法，在C语言中超长整型(long long)占8个字节，但是对于32位CPU的寄存器来讲是放不下的，因此需要用两个寄存器表示一个数，例如：
	; long long a, b, c;
	; 计算a和b的和时需要用adds指令而不是add指令，因为在计算的过程中需要先将a和b的低32位相加，如果有进位要将进位存储在CPSR寄存器的c位中，在计算高32位的时候由于还需要加上进位，因此就不能再用add指令了，需要用adc指令，即有进位的加法，汇编代码如下:
	adds r4, r0, r2 ;假设寄存器r0和r1存储了变量a的值，r0存储低32位
					;r2和r3存储了变量b的值，r2存储低32位
	adc r5, r1, r3	;该指令作用就是计算r1 + r3 + c标志位
					;和adc类似的寄存器还有减法对应的寄存器sbc
	and r1, r2, r3 ;按位与
	orr r1, r2, r3 ;按位或
	eor r1, r2, r3 ;异或
	cmp r1, r2;会执行r1 - r2，功能是比较，实际上是将两个操作数做了减法，与sub类似，cmp的目的是为了修改CPSR标志位，和sub不同的是cmp并不保存运算结果
	bic r0, r0, #0x1 ;清零，将r0的最低位清零，再赋给r0
bbb
	mov r6, #0x9000 ;
	str r5, [r6] ;将r5中的内容存储到0x9000所在的内存空间中
	str r5, [r6, 0x4] ;地址空间就是基址+偏移0x9004
	ldr r7, [r6] ;将r6内存单元中的内容取出放到r7寄存器中
	
	bl aaa ;b指令是跳转，bl是跳转并增加返回地址，该返回地址会被保存在r14寄存器中，所谓返回地址，就是bl指令的下一条指令的地址，即执行完aaa代码块的时候继续执行r14寄存器里面的值所在的内存处的指令，而b指令则不会在r14寄存器中存储返回地址
	
	swp ;交换指令
	mrs ;s代表state(状态寄存器)，r代表register(通用寄存器)，将状态寄存器中的值存入通用寄存器中
	msr ;将通用寄存器中的值存入状态寄存器中
	
	;以下汇编指令将模式变为user模式
	mrs r0, cpsr ;将CPSR中内容存入r0
	bic r0, r0, #0xf ;把r0里最低4位清零(目的是变为User模式)，结果给r0
	msr cpsr_cxsf, r0 ;
	
	end










































































































































