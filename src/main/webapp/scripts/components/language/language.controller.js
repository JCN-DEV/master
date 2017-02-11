'use strict';

angular.module('stepApp')
    .controller('LanguageController', function ($scope, $timeout, $translate, Language, tmhDynamicLocale) {
        $scope.changeLanguage = function (languageKey) {
            $translate.use(languageKey);
            tmhDynamicLocale.set(languageKey);

            $timeout(
                function() {
                    try{

                        var $inputs = $('input[required], select[required], textarea[required]');

                        if ($inputs.length > 0) {
                            $inputs.each(function() {
                                var $input = $(this);
                                if($input.prev().prop('tagName') && $input.prev().prop('tagName').toLowerCase() == 'label') {
                                    //console.log('----' + $input.attr('name'));
                                    var labelHtml = $input.prev().html();
                                    if (labelHtml.indexOf("*") == -1){
                                        $input.prev().append("<strong style='color:red'> * </strong>");
                                    }
                                }
                                else if($input.parent().parent().children(0).prop('tagName') && $input.parent().parent().children(0).prop('tagName').toLowerCase() == 'label'){
                                    //console.log('++++' + $input.attr('name'));
                                    var labelHtml = $input.parent().parent().find('label').html();
                                    //console.log(labelHtml);
                                    if (labelHtml.indexOf("*") == -1){
                                        $input.parent().parent().find('label').append("<strong style='color:red'> * </strong>");
                                    }
                                }
                            });
                        }

                    }catch(e) {console.log(e);}
                }
            , 1000);
        };

        Language.getAll().then(function (languages) {
            $scope.languages = languages;
        });
    })
    .filter('findLanguageFromKey', function () {
        return function (lang) {
            return {
                "bn": "বাংলা",
                "ca": "Català",
                "da": "Dansk",
                "de": "Deutsch",
                "en": "English",
                "es": "Español",
                "fr": "Français",
                "gl": "Galego",
                "hu": "Magyar",
                "it": "Italiano",
                "ja": "日本語",
                "ko": "한국어",
                "nl": "Nederlands",
                "pl": "Polski",
                "pt-br": "Português (Brasil)",
                "pt-pt": "Português",
                "ro": "Română",
                "ru": "Русский",
                "sv": "Svenska",
                "tr": "Türkçe",
                "zh-cn": "中文（简体）",
                "zh-tw": "繁體中文"
            }[lang];
        }
    });
