/**
 * 关于上传图片裁剪的代码
 */
/**
 * The callback functions (set with the onInit, onSelectStart, onSelectChange,
 * and onSelectEnd options) are passed two arguments. First argument is a
 * reference to the image that the plugin is attached to, the second is an
 * object representing the current selection. The object has six properties:
 * x1, y1: coordinates of the top left corner of the selected area x2, y2:
 * coordinates of the bottom right corner of the selected area width:
 * selection width height: selection height
 */

// 全局变量 保存上传图片的文件宽与高
var IMGWIDTH = 0;
var IMGHEIGHT = 0;

function preview(img, selection) {
  // 显示裁剪按钮。
  var cutImgBtn = document.getElementById("cutImgBtn");
  if (cutImgBtn.style.display == 'none') {
    cutImgBtn.style.display = 'block';
  }

  var scaleX = 150 / (selection.width);
  var scaleY = 150 / (selection.height);

  $('#previewImg').css({
    width : Math.round(scaleX * IMGWIDTH) + 'px',
    height : Math.round(scaleY * IMGHEIGHT) + 'px',
    marginLeft : '-' + Math.round(scaleX * selection.x1) + 'px',
    marginTop : '-' + Math.round(scaleY * selection.y1) + 'px'
  });
}

function recordCutImgInfo(img, selection) {
  $('input[name="x1"]').val(selection.x1);
  $('input[name="y1"]').val(selection.y1);
  $('input[name="x2"]').val(selection.x2);
  $('input[name="y2"]').val(selection.y2);
  $('input[name="imgWidth"]').val(selection.width);
  $('input[name="imgHeight"]').val(selection.height);
}

function cutImg() {
  var cutImgBtn = document.getElementById("cutImgBtn");
  if (cutImgBtn.style.display == 'block') {
    // 如果显示为块级元素，那么换成不显示该元素。
    cutImgBtn.style.display = 'none';
  }
  var ias = $('#uploadImg').imgAreaSelect({
    instance : true
  });
  ias.setOptions({
    hide : true
  });
  ias.update();
}

$(document).ready(function() {
  $('#previewImgDiv').css({
    position : 'relative',
    overflow : 'hidden',
    width : '150px',
    height : '150px'
  });

  $('#uploadImg').imgAreaSelect({
    aspectRatio : '1:1',
    onSelectChange : preview,
    onSelectEnd : recordCutImgInfo
  });
});

$(function() {
  $('#img').change(function() {
    var file = this.files[0]; // 选择上传的文件
    var reader = new FileReader();
    var image = new Image();
    reader.readAsDataURL(file); // Base64
    $(reader).load(function() {
      // $('#previewImgDiv').html('<img src="'+ this.result +'" alt="" />');
      $('#uploadImg').attr('src', this.result);
      $('#previewImg').attr('src', this.result);
      var imgData = this.result;
      // 用于加载图片的真实高度和宽度。
      var image = new Image();
      image.src = imgData;
      image.onload = function() {
        IMGWIDTH = image.width;
        IMGHEIGHT = image.height;
      }
    });
  });
});