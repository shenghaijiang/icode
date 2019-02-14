/**
 * Created by lugy on 2017/10/12.
 */
const xtit_csharp2js={
  init:function (options) {
     if(options.revice4csharp)
       this.revice4csharp=options.revice4csharp;
    if(options.send2csharp)
      this.send2csharp=options.send2csharp;
  },
  revice4csharp:function (msg) {
      alert(msg);
  },
  send2csharp:function (msg) {
      console.log(msg);
  }
}

export default xtit_csharp2js
