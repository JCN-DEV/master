/* globals $ */
'use strict';

angular.module('stepApp')
    .directive('showValidation', function($timeout) {
        return {
            restrict: 'A',
            require: 'form',
            link: function (scope, element) {
                element.find('.form-group').each(function() {
                    var $formGroup = $(this);
                    var $inputs = $formGroup.find('input[ng-model],textarea[ng-model],select[ng-model]');

                    if ($inputs.length > 0) {
                        $inputs.each(function() {
                            var $input = $(this);
                            scope.$watch(function() {
                                return $input.hasClass('ng-invalid') && $input.hasClass('ng-dirty');
                            }, function(isInvalid) {
                                $formGroup.toggleClass('has-error', isInvalid);
                            });
                        });
                    }
                });

                $timeout(
                    function() {
                        try{

                            element.find('.form-group').each(function() {
                                var $formGroup = $(this);
                                var $inputs = $formGroup.find('input[required], select[required], textarea[required]');

                                if ($inputs.length > 0) {
                                    $inputs.each(function() {
                                        var $input = $(this);
                                        if($input.prev().prop('tagName') && $input.prev().prop('tagName').toLowerCase() == 'label') {
//                                            console.log('>>>' + $input.attr('name'));
                                            $input.prev().append("<strong style='color:red'> * </strong>");
                                        }
                                        else if($input.parent().parent().children(0).prop('tagName') && $input.parent().parent().children(0).prop('tagName').toLowerCase() == 'label'){
//                                            console.log('===' + $input.attr('name'));
                                            $input.parent().parent().find('label').append("<strong style='color:red'> * </strong>");
                                        }
                                    });
                                }
                            });

                        }catch(e) {console.log(e);}
                    }
                );

            }
        };
    });
