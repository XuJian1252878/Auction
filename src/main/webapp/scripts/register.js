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
