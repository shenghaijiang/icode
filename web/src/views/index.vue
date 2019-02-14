<template>
  <!-- <div class="viewport" style="position:relative;"> -->
    <div class="welcome-box">
      <div class="welcome-box__title">欢迎使用本系统</div>
      <p class="welcome-box__tip">温馨提示：为了您更好的体验本系统，请使用 Chrome 浏览器。</p>
    </div>
  <!-- </div> -->
</template>

<style>
  .welcome-box {
    width: 520px;
    height: 360px;
    background: #C5CAE9;
    padding-top: 80px;
    box-sizing: border-box;
    transform: translate(-50%, -50%);
    position: absolute;
    left: 50%;
    top: 50%;
  }
  .welcome-box__title {
    font-size: 24px;
    color: #000;
    text-align: center;
  }
  .welcome-box__tip {
    margin-top: 8px;
    padding: 8px 16px;
    font-size: 14px;
    text-align: center;
  }
</style>

