
'use strict';

angular.module('stepApp')
    .directive('embedSrc', function () {
        return {
        restrict: 'A',
            link: function (scope, element, attrs) {
            var current = element;
            scope.$watch(function() { return attrs.embedSrc; }, function () {
                var clone = element
                .clone().attr('src', attrs.embedSrc);
                current.replaceWith(clone);
                current = clone;
                });
            }
        };
    })
    .directive('customEmail', function() {
        var EMAIL_REGEXP = /^[a-z0-9!#$%&'*+/=?^_`{|}~.-]+@[a-z]+\.[a-z.]{2,5}$/i;
        return {
            require: '?ngModel',
            link: function(scope, elm, attrs, ctrl) {
                // only apply the validator if ngModel is present and Angular has added the email validator
                if (ctrl && ctrl.$validators.email) {

                    // this will overwrite the default Angular email validator
                    ctrl.$validators.email = function(modelValue) {
                        return ctrl.$isEmpty(modelValue) || EMAIL_REGEXP.test(modelValue);
                    };
                }
            }
        };
    }).directive('numericOnly', function(){
    return {
        require: '?ngModel',
        link: function(scope, element, attrs, modelCtrl) {

            modelCtrl.$parsers.push(function (inputValue) {
                var transformedInput = inputValue ? inputValue.replace(/[^\d.-]/g,'') : null;

                if (transformedInput!=inputValue) {
                    modelCtrl.$setViewValue(transformedInput);
                    modelCtrl.$render();
                }

                return transformedInput;
            });
        }
    };
});
angular.module('stepApp').filter('unique', function () {

    return function (items, filterOn) {

        if (filterOn === false) {
            return items;
        }

        if ((filterOn || angular.isUndefined(filterOn)) && angular.isArray(items)) {
            var hashCheck = {}, newItems = [];

            var extractValueToCompare = function (item) {
                if (angular.isObject(item) && angular.isString(filterOn)) {
                    return item[filterOn];
                } else {
                    return item;
                }
            };

            angular.forEach(items, function (item) {
                var valueToCheck, isDuplicate = false;

                for (var i = 0; i < newItems.length; i++) {
                    if (angular.equals(extractValueToCompare(newItems[i]), extractValueToCompare(item))) {
                        isDuplicate = true;
                        break;
                    }
                }
                if (!isDuplicate) {
                    newItems.push(item);
                }

            });
            items = newItems;
        }
        return items;
    };
}).filter("filterDeadline", function($rootScope) {
    return function(items) {
        var df = getCurrentDate();
        var result = [];
        for (var i=0; i<items.length; i++){
            var tf = Math.abs(parseDate(df) * 1000),
                tt = Math.abs(parseDate(items[i].applicationDeadline) * 1000);

            //console.log(tf);
            //console.log(tt);

            if (tt <= tf)  {
                result.push(items[i]);
            }
        }
        return result;
    };
});;

function parseDate(input) {
    var parts = input.split('-');
    return new Date(parts[2], parts[1]-1, parts[0]);
}

function getCurrentDate()
{
    var today = new Date();
    var cDate = today.getDate();
    var cMonth = parseInt(today.getMonth()) + 1;

    if(cMonth<=9) {
        cMonth = '0'+cMonth;
    }

    var cYear = today.getFullYear();

    return cYear + '-' + cMonth + '-' + cDate;
}

