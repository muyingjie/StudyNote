ES6

let a = ()=>{};
console.log(a()); //undefined

let a = ()=>({});
console.log(a()); //{}

let a = ()=>{
	return 5;
};
console.log(a()); //5

let b = {
	name: "bb"
};
b.a = function(){
	let a = ()=>{
		return this;
	};
	
	return a();
};
console.log(b.a()); // 返回b对象而不是window

箭头函数中没有arguments对象


// 剩余参数rest语法
function fn(...re){
	console.log(re); //[1, 2, 3, 4]
}
fn(1,2,3,4);

function fn(normal, ...re){
	console.log(re); //[2, 3, 4]
}
fn(1,2,3,4);

// spread语法：基于剩余参数rest语法实现
function fn(normal, ...re){
	// 所谓spread就是可以把re这个数组展开，也能把一个对象展开，把对象展开是ES7中的内容
	ott(...re);
}
fn(1,2,3,4);
function ott(a, b, c){
	console.log(a + b + c); // 9
}

模板字符串``
let firstName = 'aaa';
let lastName = 'bbb';
let str = `My name is ${firstName} ${lastName}`;


解构赋值
let human = {
	id: 0,
	name: "flowke"
};
let {id, name} = human; // 这句话相当于在全局作用域下定义了两个变量id和name，并分别被初始化为human对象下id和name属性值


// 如果希望变量名和human对象中属性名有所区别时，可以写成冒号后跟新的变量名：
let human = {
	id: 0,
	name: "flowke"
};
let {id:id1, name:name1} = human; //定义了两个名字分别为id1和name1的变量，它们两个的值分别对应human对象中id和name属性

// 复杂一些的解构
let human = {
	id: 0,
	name: "flowke",
	children: {
		c1: "Rosa",
		c2: "Sally"
	}
};

let {id:id1, name, children:{c1,c2}} = human; //定义了4个变量，名字分别为id name c1 c2，值分别为0 "flowke" "Rosa" "Sally"


// human中并不一定所有的属性都解构
let human = {
	id: 0,
	name: "flowke"
};
let {name} = human; //只定义了一个name变量


// 类
class Animal {
	constructor(a, b, c){
		this.a = a;
		this.b = b;
		this.c = c;
	}
	
	move(){}
}

class Human extends Animal{
	constructor(name){
		super(2, 3, 4);
		this.name = name;
	}
}
let a1 = new Animal(1, 2, 3);



















