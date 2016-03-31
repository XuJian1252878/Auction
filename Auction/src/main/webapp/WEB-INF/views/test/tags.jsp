<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="../../../template/header.jsp"%>

<link href="template/bootstrap-tagsinput/src/bootstrap-tagsinput.css" rel="stylesheet">
<link href="styles/test/typeahead-examples.css" rel="stylesheet">

<script type="text/javascript" src="template/bootstrap-tagsinput/src/bootstrap-tagsinput.js"></script>
<script type="text/javascript" src="scripts/typeahead.bundle.js"></script>

<div class="container">
  <div class="row">
    <p>Markup</p>
    <div class="col-lg-10 col-lg-offset-1">
      <input type="text" value="Amsterdam,Washington,Sydney,Beijing,Cairo" data-role="tagsinput" />
    </div><br />
    <p>True multi value</p>
    <div class="col-lg-10 col-lg-offset-1">
      <select multiple data-role="tagsinput">
        <option value="Amsterdam">Amsterdam</option>
        <option value="Washington">Washington</option>
        <option value="Sydney">Sydney</option>
        <option value="Beijing">Beijing</option>
        <option value="Cairo">Cairo</option>
      </select>
    </div><br />
    <p>Typeahead</p>
    <div class="col-lg-10 col-lg-offset-1">
      <input type="text" value="Amsterdam,Washington" data-role="tagsinput" />
      <script type="text/javascript">
        
      </script>
    </div><br />
    <p> typeahead js 测试</p>
    <p>When initializing a typeahead using the typeahead.js jQuery plugin, you pass the plugin method one or more datasets. The source of a dataset is responsible for computing a set of suggestions for a given query.</p>
    <div class="col-lg-10 col-lg-offset-1">
      <div id="the-basics">
        <input class="typeahead" type="text" placeholder="States of USA">
        <script type="text/javascript">
          var substringMatcher = function(strs) {
            return function findMatches(q, cb) {
              var matches, substrRegex;
              // an array that will be populated with substring matches
              matches = [];
              // regex used to determine if a string contains the substring `q`
              substrRegex = new RegExp(q, 'i');  // i 表示不包含 q 就返回true。
              // iterate through the pool of strings and for any string that
              // contains the substring `q`, add it to the `matches` array
              $.each(strs, function(i, str) {
                if (substrRegex.test(str)) {
                  matches.push(str);
                }
              });
              cb(matches);  // 这句话是什么鬼，表示返回吗？
            };
          };
          // 同时也为下面一个控件提供数据源。
          var states = ['Alabama', 'Alaska', 'Arizona', 'Arkansas', 'California',
                        'Colorado', 'Connecticut', 'Delaware', 'Florida', 'Georgia', 'Hawaii',
                        'Idaho', 'Illinois', 'Indiana', 'Iowa', 'Kansas', 'Kentucky', 'Louisiana',
                        'Maine', 'Maryland', 'Massachusetts', 'Michigan', 'Minnesota',
                        'Mississippi', 'Missouri', 'Montana', 'Nebraska', 'Nevada', 'New Hampshire',
                        'New Jersey', 'New Mexico', 'New York', 'North Carolina', 'North Dakota',
                        'Ohio', 'Oklahoma', 'Oregon', 'Pennsylvania', 'Rhode Island',
                        'South Carolina', 'South Dakota', 'Tennessee', 'Texas', 'Utah', 'Vermont',
                        'Virginia', 'Washington', 'West Virginia', 'Wisconsin', 'Wyoming'];
          $('#the-basics .typeahead').typeahead({
            hint: true,
            highlight: true,
            minLength: 1
          },
          {
            name: 'status',
            source: substringMatcher(states)
          })
        </script>
      </div>
    </div><br />
    <p>Bloodhound (Suggestion Engine)</p>
    <p>For more advanced use cases, rather than implementing the source for your dataset yourself, you can take advantage of Bloodhound, the typeahead.js suggestion engine.

Bloodhound is robust, flexible, and offers advanced functionalities such as prefetching, intelligent caching, fast lookups, and backfilling with remote data.</p>
    <div id="bloodhound">
      <input class="typeahead" type="text" placeholder="States of USA">
      <script type="text/javascript">
        // constructs the suggestion engine
        var states = new Bloodhound({
          datumTokenizer: Bloodhound.tokenizers.whitespace,
          queryTokenizer: Bloodhound.tokenizers.whitespace,
          // `states` is an array of state names defined in "The Basics"
          local: states
        });
  
        $('#bloodhound .typeahead').typeahead({
          hint: true,
          highlight: true,
          minLength: 1
        },
        {
          name: 'states',
          source: states
        });
      </script>
    </div><br />
    <p>Prefetch</p>
    <p>Prefetched data is fetched and processed on initialization. If the browser supports local storage, the processed data will be cached there to prevent additional network requests on subsequent page loads.</p>
    <div id="prefetch">
      <input class="typeahead" type="text" placeholder="Countries">
      <script type="text/javascript">
        var countries = new Bloodhound({
          datumTokenizer: Bloodhound.tokenizers.whitespace,
          queryTokenizer: Bloodhound.tokenizers.whitespace,
          // url points to a json file that contains an array of country names, see
          // https://github.com/twitter/typeahead.js/blob/gh-pages/data/countries.json
          prefetch: 'data/test/countries.json'
        });
  
        // passing in `null` for the `options` arguments will result in the default
        // options being used
        $('#prefetch .typeahead').typeahead(null, {
          name: 'mytest',
          // 以json中的某一个属性作为值。source后面跟的是json的属性名称。
          source: mytest
        });
      </script>
    </div><br />
    <p>Remote</p>
    <p></p>
  </div>
</div>

<%@ include file="../../../template/footer.jsp"%>