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


/**
 * 
 */

var placeSearch, autocomplete;
var componentForm = {
  route : 'long_name',
  locality : 'long_name',
  administrative_area_level_2 : 'long_name',
  administrative_area_level_1 : 'short_name',
  country : 'long_name',
  postal_code : 'short_name'
};

function initAutocomplete() {
  // Create the autocomplete object, restricting the search to geographical
  // location types.
  autocomplete = new google.maps.places.Autocomplete(
  /** @type {!HTMLInputElement} */
  (document.getElementById('autocomplete')), {
    types : [ 'geocode' ]
  });

  // When the user selects an address from the dropdown, populate the address
  // fields in the form.
  autocomplete.addListener('place_changed', fillInAddress);
}

// [START region_fillform]
function fillInAddress() {
  // Get the place details from the autocomplete object.
  var place = autocomplete.getPlace();

  for ( var component in componentForm) {
    document.getElementById(component).value = '';
    document.getElementById(component).disabled = false;

  }

  // Get each component of the address from the place details
  // and fill the corresponding field on the form.
  for (var i = 0; i < place.address_components.length; i++) {
    var addressType = place.address_components[i].types[0];
    if (componentForm[addressType]) {
      var val = place.address_components[i][componentForm[addressType]];
      document.getElementById(addressType).value = val;
    }
  }
}
// [END region_fillform]

// [START region_geolocation]
// Bias the autocomplete object to the user's geographical location,
// as supplied by the browser's 'navigator.geolocation' object.
function geolocate() {
  if (navigator.geolocation) {
    navigator.geolocation.getCurrentPosition(function(position) {
      var geolocation = {
        lat : position.coords.latitude,
        lng : position.coords.longitude
      };
      var circle = new google.maps.Circle({
        center : geolocation,
        radius : position.coords.accuracy
      });
      autocomplete.setBounds(circle.getBounds());
    });
  }
}

$(function() {

  $('body').on('click', '.list-group .list-group-item', function() {
    $(this).toggleClass('active');
  });
  $('.list-arrows button').click(function() {
    var $button = $(this), actives = '';
    if ($button.hasClass('move-left')) {
      actives = $('.list-right ul li.active');
      actives.clone().appendTo('.list-left ul');
      actives.remove();
    } else if ($button.hasClass('move-right')) {
      actives = $('.list-left ul li.active');
      actives.clone().appendTo('.list-right ul');
      actives.remove();
    }
  });
  $('.dual-list .selector').click(
      function() {
        var $checkBox = $(this);
        if (!$checkBox.hasClass('selected')) {
          $checkBox.addClass('selected').closest('.well').find(
              'ul li:not(.active)').addClass('active');
          $checkBox.children('i').removeClass('glyphicon-unchecked').addClass(
              'glyphicon-check');
        } else {
          $checkBox.removeClass('selected').closest('.well').find(
              'ul li.active').removeClass('active');
          $checkBox.children('i').removeClass('glyphicon-check').addClass(
              'glyphicon-unchecked');
        }
      });
  $('[name="SearchDualList"]').keyup(function(e) {
    var code = e.keyCode || e.which;
    if (code == '9')
      return;
    if (code == '27')
      $(this).val(null);
    var $rows = $(this).closest('.dual-list').find('.list-group li');
    var val = $.trim($(this).val()).replace(/ +/g, ' ').toLowerCase();
    $rows.show().filter(function() {
      var text = $(this).text().replace(/\s+/g, ' ').toLowerCase();
      return !~text.indexOf(val);
    }).hide();
  });

});
