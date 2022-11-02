function draw() {
  var canvas = document.getElementById('canvas');
  if (canvas.getContext) {
    var ctx = canvas.getContext('2d');



    // //example code=============================================================
    // ctx.fillStyle = 'rgba(0,200,0,0.5)';
    // //           x,y,width,height
    // ctx.fillRect(100,100,100,100);
    //
    // ctx.fillStyle = 'rgba(0,0,200,0.5)';
    // ctx.beginPath();
    // ctx.moveTo(50,50);
    // ctx.lineTo(0,75);
    // ctx.lineTo(300,300);
    // ctx.moveTo(250,200);
    // ctx.lineTo(275,0);
    // ctx.lineTo(10,175);
    // ctx.fill();
    //
    // ctx.strokeStyle = 'rgb(200,0,0)';
    // ctx.beginPath();
    // ctx.moveTo(125,125);
    // ctx.lineTo(125,45);
    // ctx.lineTo(45,125);
    // ctx.closePath();
    // ctx.stroke();
    //
    // //arc(x, y, radius, startAngle, endAngle, counterclockwise)
    // //arcTo(x1, y1, x2, y2, radius)
    // //angles are in RADIANS. counterclockwise is a boolean.
    // ctx.fillStyle = 'rgba(0,100,100,0.5)';
    // ctx.beginPath();
    // ctx.arc(30,245,120,0,Math.PI,true);
    // ctx.arc(250,250,50,3*Math.PI/2,3*Math.PI/2+2*Math.PI, false);
    // ctx.closePath();
    // ctx.fill();
//draw flower shape
    ctx.globalAlpha = 0.2;
    // ctx.fillStyle = "rgba(200,0,0,1)"; //globalAlpha overrides this one
    // var l = ctx.createLinearGradient(150,150,300,300);
    // l.addColorStop(0,'blue');
    // l.addColorStop(1,'red');
    // ctx.fillStyle = l;

    //createRadialGradient(h1,k1,r1,h2,k2,r2)
    var l = ctx.createRadialGradient(150,150,0,150,150,100);
    l.addColorStop(0,'blue');
    l.addColorStop(1,'red');
    ctx.fillStyle = l;

    for (var i = 0; i < 8; i++) {
      var c = 1.0*i*Math.PI/4.0;
      var x = 150 + 50*Math.cos(c);
      var y;
      if (i < 4) {
        y = Math.sqrt(2500-(x-150)*(x-150))+150;
      }
      else {
        y = -1*Math.sqrt(2500-(x-150)*(x-150))+150;
      }

      // var l = ctx.createRadialGradient(x,y,0,x,y,50);
      // l.addColorStop(0,'white');
      // l.addColorStop(1,'red');
      // ctx.fillStyle = l;

      ctx.beginPath();
      ctx.arc(x,y,100,i*2.0*Math.PI/8.0,i*2.0*Math.PI/8.0+Math.PI,true);
      ctx.closePath();
      ctx.fill();
    }

//night sky
    // var l = ctx.createRadialGradient(275,25,10,275,25,150);
    // l.addColorStop(0,'#0000ff');
    // l.addColorStop(1,'#000066');
    // ctx.fillStyle = l;
    // ctx.fillRect(0,0,300,300);
    //
    // function star() {
    //   ctx.beginPath();
    //   var r = Math.floor(Math.random()*4+1);
    //   var h = Math.floor(Math.random()*290+5);
    //   var k = Math.floor(Math.random()*290+5);
    //
    //   ctx.moveTo(h,k);
    //
    // }
  }
}
