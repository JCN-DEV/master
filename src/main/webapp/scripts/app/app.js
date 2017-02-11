'use strict';

angular.module('stepApp', ['LocalStorageModule', 'tmh.dynamicLocale', 'pascalprecht.translate','ui.bootstrap', 'ui.router', 'ngBreadCrumb',
    'ui.bootstrap','ngResource', 'ui.router', 'ngCookies', 'ngAria', 'ngCacheBuster', 'ngFileUpload', 'infinite-scroll',
    'angular-loading-bar', 'remoteValidation', 'angularUtils.directives.dirPagination', 'googlechart', 'tree.service', 'tree.directives', 'angularUtils.directives.dirPagination'])

    .run(function ($rootScope, $location, $window, $http, $state, $modal,  $translate, Language, Auth, Principal, ENV, VERSION) {
        // update the window title using params in the following
        // precendence
        // 1. titleKey parameter
        // 2. $state.$current.data.pageTitle (current state page title)
        // 3. 'global.title'
        $rootScope.encCre='40d90577f7a7c727c77677ba2e1f77bc1df98fcf8aa24c225e84511cbc125d6026a532bd808c74c4d4896c11b7f033f675b143e83cc146ae4a41d34fc3686e3e6f37ae0be3d03d6dd62fd17d8cae08ad5c1c518ba4e4833905643bbdc85d161fb587da2abaa8b83f0e825d534a953c1977de6d50a36a06fa1fcb2cc53fe822ee';

        //for auto calendar when click on text input
        $rootScope.calendar = {
            opened: {},
            dateFormat: 'yyyy-MM-dd',
            dateOptions: {},
            open: function ($event, which) {
                $event.preventDefault();
                $event.stopPropagation();
                $rootScope.calendar.opened[which] = true;
            }
        };
        //$rootScope.clientIpAddress ='';
        $rootScope.setActionMessage = function (message) {
            $rootScope.closeAlert();
            $('.message.error').show();
            localStorage.setItem('globalActionMessage', message);
            $rootScope.globalActionMessage= localStorage.getItem('globalActionMessage');
            setTimeout(function() { $(".message.error").hide(); }, 2000000000);
        }

        $rootScope.setErrorMessage = function (message) {
            $rootScope.closeAlert();
            $('.message.error').show();
            localStorage.setItem('globalErrorMessage', message);
            $rootScope.globalErrorMessage= localStorage.getItem('globalErrorMessage');
            setTimeout(function() { $(".message.error").hide(); }, 2000000000);
        }

        $rootScope.setSuccessMessage = function (message) {
            $rootScope.closeAlert();
            $('.message.success').show();
            localStorage.setItem('globalSuccessMessage',message);
            $rootScope.globalSuccessMessage= localStorage.getItem('globalSuccessMessage');
            setTimeout(function() { $(".message.success").hide(); }, 200000000);
        }

        $rootScope.setWarningMessage = function (message) {
            $rootScope.closeAlert();
            $('.message.warning').show();
            localStorage.setItem('globalWarningMessage',message);
            $rootScope.globalWarningMessage= localStorage.getItem('globalWarningMessage');
            setTimeout(function() { $(".message.warning").hide(); }, 200000000);
        }

        // for user roles
        $rootScope.assetRefId ='';
        $rootScope.userRoles = 'USER_USER';
        $rootScope.VERSION = VERSION;
        $rootScope.$on('$stateChangeStart', function (event, toState, toStateParams) {
            console.log("to state : "+toState);
            console.log(toState);
            if(toState.name === 'mainApply'){
                console.log('trying to apply for job');
                $rootScope.setSuccessMessage('please sign up and login');
            }
            console.log("to state : "+event);
            console.log(event);
            console.log("to state : "+toStateParams);
            console.log(toStateParams);
            Principal.identity().then(function (account) {



                if(account != null && account.authorities.length > 0){
                    $rootScope.userRoles = account.authorities;
                    console.log('$rootScope.userRoles : '+$rootScope.userRoles);
                }else{
                    console.log('GOT NULL');
                }

                if(account !=null && account.desigShort !=null){
                    $rootScope.accountName = account.desigShort;
                }else if(account !=null && account.firstName !=null){
                    $rootScope.accountName = account.firstName;
                }else if(account !=null && account.login !=null){
                    $rootScope.accountName = account.login;
                }

            });
            $rootScope.toState = toState;
            $rootScope.assetRefId ='';
            //console.log($rootScope.toState.toSource());
            //console.log('state >>>>>>>>>>>>>>>>>>> '+$rootScope.toState.name);
            $rootScope.clientIpAddress ='';

            //$http.get('https://api.ipify.org')
            //    .then(function(response) {
            //        $rootScope.clientIpAddress = response.data;
            //        console.log( $rootScope.clientIpAddress);
            //    });


            if($rootScope.toState != null){
                $rootScope.browser = navigator.userAgent;
                $rootScope.hostnames = $location.host();

                $rootScope.auditLogs = {};

                console.log($rootScope.browser);
                //console.log($rootScope.clientIpAddress);
                //console.log($rootScope.hostnames);

                $rootScope.auditLogs.event = $rootScope.toState.name;
                $rootScope.auditLogs.userBrowser = $rootScope.browser;
                //$rootScope.auditLogs.userIpAddress = $rootScope.clientIpAddress;
                //$rootScope.auditLogs.deviceName = $rootScope.hostnames;
                $rootScope.auditLogs.eventType = 'User Page Access';

                /*var data = $.param({
                    event: $rootScope.toState.name,
                    userBrowser: $rootScope.browser,
                    userIpAddress: $rootScope.clientIpAddress,
                    deviceName: $rootScope.hostnames,
                    eventType: 'User Page Access'
                });

                var config = {
                    headers : {
                        'Content-Type': 'application/x-www-form-urlencoded;charset=utf-8;'
                    }
                }*/
                //console.log('$rootScope.accountName : >>>>>>>>>>>>>>> '+$rootScope.accountName);

                if($rootScope.accountName != 'Login'){
                    $http({
                        method  : 'POST',
                        url     : 'api/auditLogs',
                        data    : $rootScope.auditLogs
                    })
                        .success(function(data) {
                            if (data.errors) {
                                // Showing errors.
                                $rootScope.errorName = data.errors.name;
                                $rootScope.errorUserName = data.errors.username;
                                $rootScope.errorEmail = data.errors.email;
                            } else {
                                $rootScope.message = data.message;
                            }
                        });
                }


                /*$http.post('api/createAuditLogUI', data)
                    .success(function (data, status, headers, config) {
                        $rootScope.PostDataResponse = data;
                    })
                    .error(function (data, status, header, config) {
                        $rootScope.ResponseDetails = "Data: " + data +
                            "<hr />status: " + status +
                            "<hr />headers: " + header +
                            "<hr />config: " + config;
                    });*/
                /*$http.get('/api/createAuditLogUI').then(function(value) {
                    $scope.example7 = value.status;
                }).finally(function() {
                    $scope.example7 += " (Finally called)";
                });*/

            }


            //console.log($rootScope.toState.toSource());
           // console.log('state >>>>>>>>>>>>>>>>>>> '+$rootScope.toState.name);
            $rootScope.globalErrorMessage= localStorage.getItem('globalErrorMessage');


            if($rootScope.globalErrorMessage == null){
                $rootScope.globalErrorMessage='';
            }
            else{
                $rootScope.globalErrorMessage = $rootScope.globalErrorMessage;
                localStorage.removeItem('globalErrorMessage');
            }
            $rootScope.globalSuccessMessage= localStorage.getItem('globalSuccessMessage');
            if($rootScope.globalSuccessMessage == null){
                $rootScope.globalSuccessMessage='';
            }
            else{
                $rootScope.globalSuccessMessage = $rootScope.globalSuccessMessage;
                localStorage.removeItem('globalSuccessMessage');
            }
            //$rootScope.globalMessage = globalMessage;
            $rootScope.toStateParams = toStateParams;

            if (Principal.isIdentityResolved()) {
                Auth.authorize();
            }

            // Update the language
            Language.getCurrent().then(function (language) {
                $translate.use(language);
            });

        });  //end authorities

        $rootScope.clickedApproved = true;

        var updateTitle = function(titleKey) {
            if (!titleKey && $state.$current.data && $state.$current.data.pageTitle) {
                titleKey = $state.$current.data.pageTitle;
            }
            $translate(titleKey || 'global.title').then(function (title) {
                $window.document.title = title;
            });
        };

        $rootScope.setActionMessage = function (message) {
            $rootScope.closeAlert();
            $('.message.error').show();
            localStorage.setItem('globalActionMessage', message);
            $rootScope.globalActionMessage= localStorage.getItem('globalActionMessage');
            setTimeout(function() { $(".message.error").hide(); }, 2000000000);
        }

        $rootScope.setErrorMessage = function (message) {
            $rootScope.closeAlert();
            $('.message.error').show();
            localStorage.setItem('globalErrorMessage', message);
            $rootScope.globalErrorMessage= localStorage.getItem('globalErrorMessage');
            setTimeout(function() { $(".message.error").hide(); }, 2000000000);
        }

        $rootScope.setSuccessMessage = function (message) {
            $rootScope.closeAlert();
            $('.message.success').show();
            localStorage.setItem('globalSuccessMessage',message);
            $rootScope.globalSuccessMessage= localStorage.getItem('globalSuccessMessage');
            setTimeout(function() { $(".message.success").hide(); }, 200000000);
        }

        $rootScope.setWarningMessage = function (message) {
            $rootScope.closeAlert();
            $('.message.warning').show();
            localStorage.setItem('globalWarningMessage',message);
            $rootScope.globalWarningMessage= localStorage.getItem('globalWarningMessage');
            setTimeout(function() { $(".message.warning").hide(); }, 200000000);
        }

        $rootScope.b64toBlob = function b64toBlob(b64Data, contentType, sliceSize) {
            contentType = contentType || '';
            sliceSize = sliceSize || 512;

            var byteCharacters = atob(b64Data);
            var byteArrays = [];

            for (var offset = 0; offset < byteCharacters.length; offset += sliceSize) {
                var slice = byteCharacters.slice(offset, offset + sliceSize);

                var byteNumbers = new Array(slice.length);
                for (var i = 0; i < slice.length; i++) {
                    byteNumbers[i] = slice.charCodeAt(i);
                }

                var byteArray = new Uint8Array(byteNumbers);

                byteArrays.push(byteArray);
            }

            var blob = new Blob(byteArrays, {type: contentType});
            return blob;
        }


        $rootScope.viewerObject = {};
        $rootScope.showFilePreviewModal = function()
        {
            $modal.open({
                templateUrl: 'scripts/app/main/common-file-viewer.html',
                controller: [
                    '$scope', '$modalInstance', function($scope, $modalInstance) {
                        $scope.ok = function() {
                            $rootScope.viewerObject = {};
                            $modalInstance.close();
                        };
                        $scope.closeModal = function() {
                            $rootScope.viewerObject = {};
                            $modalInstance.dismiss();
                        };
                    }
                ]
            });
        };

        $rootScope.showPreviewModal = function()
        {
            $modal.open({
                templateUrl: 'scripts/app/entities/hrm/hrm-fileviewer.html',
                controller: [
                    '$scope', '$modalInstance', function($scope, $modalInstance) {
                        $scope.ok = function() {
                            $rootScope.viewerObject = {};
                            $modalInstance.close();
                        };
                        $scope.closeModal = function() {
                            $rootScope.viewerObject = {};
                            $modalInstance.dismiss();
                        };
                    }
                ]
            });
        };
        $rootScope.currentPage = 1;
        $rootScope.pageSize = 30;
        // For Confirmation PopUp
        $rootScope.confirmationObject = {};
        $rootScope.showConfirmation = function()
        {
            $modal.open({
                templateUrl: 'scripts/app/main/common-confirmation.html',
                controller: [
                    '$scope', '$modalInstance', function($scope, $modalInstance) {
                        $scope.ok = function() {
                            $rootScope.confirmationObject = {};
                            $modalInstance.close();
                        };
                        $scope.closeModal = function() {
                            $rootScope.confirmationObject = {};
                            $modalInstance.dismiss();
                        };
                    }
                ]
            });
        };

        $rootScope.closeAlert = function() {
            $('.message.error, .message.success, .message.warning').hide();
            $rootScope.globalErrorMessage = '';
            $rootScope.globalSuccessMessage = '';
            $rootScope.globalWarningMessage = '';
        }

        $rootScope.ENV = ENV;
        $rootScope.globalErrorMessage = '';
        $rootScope.globalSuccessMessage = '';
        $rootScope.globalWarningMessage = '';
        $rootScope.accountName = 'Login';
        $rootScope.accountId = 0;
        $rootScope.VERSION = VERSION;
        //$rootScope.accountAccessAuth = 'NotLogined';
        //$rootScope.notifications = [];
        $rootScope.$on('$stateChangeStart', function (event, toState, toStateParams) {
            Principal.identity().then(function (account) {
                if(account !=null && account.id !=null){
                    $rootScope.accountId =  account.id;
                }
                if(account !=null && account.desigShort !=null){
                    $rootScope.accountName = account.desigShort;
                }else if(account !=null && account.firstName !=null){
                    $rootScope.accountName = account.firstName;
                }else if(account !=null && account.login !=null){
                    $rootScope.accountName = account.login;
                }

            });

            $rootScope.toState = toState;
            console.log($rootScope.toState);
            console.log(toStateParams);

            $rootScope.globalErrorMessage= localStorage.getItem('globalErrorMessage');
            if($rootScope.globalErrorMessage == null){
                $rootScope.globalErrorMessage='';
            }
            else{
                $rootScope.globalErrorMessage = $rootScope.globalErrorMessage;
                localStorage.removeItem('globalErrorMessage');
            }

            $rootScope.globalSuccessMessage= localStorage.getItem('globalSuccessMessage');
            if($rootScope.globalSuccessMessage == null){
                $rootScope.globalSuccessMessage='';
            }
            else{
                $rootScope.globalSuccessMessage = $rootScope.globalSuccessMessage;
                localStorage.removeItem('globalSuccessMessage');
            }

            $rootScope.globalWarningMessage= localStorage.getItem('globalWarningMessage');
            if($rootScope.globalWarningMessage == null){
                $rootScope.globalWarningMessage='';
            }
            else{
                $rootScope.globalWarningMessage = $rootScope.globalWarningMessage;
                localStorage.removeItem('globalWarningMessage');
            }
            //$rootScope.globalMessage = globalMessage;
            $rootScope.toStateParams = toStateParams;

            if (Principal.isIdentityResolved()) {
                Auth.authorize();
            }

            // Update the language
            Language.getCurrent().then(function (language) {
                $translate.use(language);
            });

        });

        $rootScope.dayDiff = function(firstDate,secondDate){
            var date2 = new Date($rootScope.formatString(secondDate));
            var date1 = new Date($rootScope.formatString(firstDate));
            var timeDiff = Math.abs(date2.getTime() - date1.getTime());
            var diffDays = Math.floor(timeDiff / (1000 * 3600 * 24));
            return diffDays+1;
        }

        $rootScope.generateYearList = function (fromYear, yearRange) {
            var yearList = [];
            var offset = 0;
            //var currentYear = new Date().getFullYear();
            for (var indx = 0; indx < yearRange; indx++) {
                yearList.push(fromYear + offset + indx);
            }
            return yearList;
        };

        $rootScope.calculateAge = function(birthday) {
            var ageDifMs = Date.now() - new Date(birthday);
            var ageDate = new Date(ageDifMs);
            return Math.abs(ageDate.getUTCFullYear() - 1970);
        };

        $rootScope.formatString = function(format) {
            var year   = parseInt(format.substring(0,4));
            var month  = parseInt(format.substring(5,7));
            var day    = parseInt(format.substring(8,10));
            var date = new Date(year, month, day);
            return date;
        }

        $rootScope.refreshRequiredFields = function() {
            $("form" ).find('.form-group').each(function() {
                    var $formGroup = $(this);
                    var $inputs = $formGroup.find('input[required], select[required], textarea[required]');

                    if ($inputs.length > 0) {
                        $inputs.each(function() {
                            var $input = $(this);
//                            console.log($input);
                            if($input.prev().prop('tagName') && $input.prev().prop('tagName').toLowerCase() == 'label') {
//                                console.log('.........' + $input.prev().html())
//                                console.log('%%%%%' + $input.attr('name'));
                                if($input.prev().html().indexOf('*') == -1) {
//                                    console.log($input.prev().html().search("<strong style='color:red'> * </strong>"));
                                    $input.prev().append("<strong style='color:red'> * </strong>");
                                }
                            }
                            else if($input.parent().parent().children(0).prop('tagName') && $input.parent().parent().children(0).prop('tagName').toLowerCase() == 'label'){
//                                console.log('^^^^^^^^' + $input.parent().parent().find('label').html());
//                                console.log('$$$$' + $input.attr('name'));
                                if($input.parent().parent().find('label').html().indexOf('*') == -1){
//                                    console.log($input.parent().parent().find('label').html().search("<strong style='color:red'> * </strong>"));
                                    $input.parent().parent().find('label').append("<strong style='color:red'> * </strong>");
                                }
                            }
                        });
                    }
                });
        }

        $rootScope.$on('$stateChangeSuccess',  function(event, toState, toParams, fromState, fromParams) {
            var titleKey = 'global.title' ;

            // Remember previous state unless we've been redirected to login or we've just
            // reset the state memory after logout. If we're redirected to login, our
            // previousState is already set in the authExpiredInterceptor. If we're going
            // to login directly, we don't want to be sent to some previous state anyway
            if (toState.name != 'login' && $rootScope.previousStateName) {
              $rootScope.previousStateName = fromState.name;
              $rootScope.previousStateParams = fromParams;

            }

            // Set the page title key to the one configured in state or use default one
            if (toState.data.pageTitle) {
                titleKey = toState.data.pageTitle;
            }
            updateTitle(titleKey);
        });

        // if the current translation changes, update the window title
        $rootScope.$on('$translateChangeSuccess', function() { updateTitle(); });


        $rootScope.back = function() {
            // If previous state is 'activate' or do not exist go to 'home'
            if ($rootScope.previousStateName === 'activate' || $state.get($rootScope.previousStateName) === null) {
                $state.go('home');
            } else {
                $state.go($rootScope.previousStateName, $rootScope.previousStateParams);
            }
        };

        $rootScope.searchByTrackID = false;

        $rootScope.logout = function () {
            Auth.logout();
            $state.go('home');
        };
    })
    .config(function ($stateProvider, $urlRouterProvider, $httpProvider, $locationProvider, $translateProvider, tmhDynamicLocaleProvider, httpRequestInterceptorCacheBusterProvider, AlertServiceProvider) {
        // uncomment below to make alerts look like toast
        //AlertServiceProvider.showAsToast(true);

        //ChartJsProvider.setOptions({ colours : [ '#FF3333', '#ACE297'] });


        //enable CSRF
        $httpProvider.defaults.xsrfCookieName = 'CSRF-TOKEN';
        $httpProvider.defaults.xsrfHeaderName = 'X-CSRF-TOKEN';

        //Cache everything except rest api requests
        httpRequestInterceptorCacheBusterProvider.setMatchlist([/.*api.*/, /.*protected.*/], true);

        $urlRouterProvider.otherwise('/');
        $stateProvider.state('site', {
            'abstract': true,
            views: {
                'navbar@': {
                    templateUrl: 'scripts/components/navbar/navbar.html',
                    controller: 'NavbarController'
                }
            },
            resolve: {
                authorize: ['Auth',
                    function (Auth) {
                        return Auth.authorize();
                    }
                ],
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        });

        $httpProvider.interceptors.push('errorHandlerInterceptor');
        $httpProvider.interceptors.push('authExpiredInterceptor');
        $httpProvider.interceptors.push('notificationInterceptor');

        // Initialize angular-translate
        $translateProvider.useLoader('$translatePartialLoader', {
            urlTemplate: 'i18n/{lang}/{part}.json'
        });

        $translateProvider.preferredLanguage('en');
        $translateProvider.useCookieStorage();
        $translateProvider.useSanitizeValueStrategy('escaped');
        $translateProvider.addInterpolation('$translateMessageFormatInterpolation');

        tmhDynamicLocaleProvider.localeLocationPattern('bower_components/angular-i18n/angular-locale_{{locale}}.js');
        tmhDynamicLocaleProvider.useCookieStorage();
        tmhDynamicLocaleProvider.storageKey('NG_TRANSLATE_LANG_KEY');

    })
    .config(['$urlMatcherFactoryProvider', function($urlMatcherFactory) {
        $urlMatcherFactory.type('boolean', {
            name : 'boolean',
            decode: function(val) { return val == true ? true : val == "true" ? true : false },
            encode: function(val) { return val ? 1 : 0; },
            equals: function(a, b) { return this.is(a) && a === b; },
            is: function(val) { return [true,false,0,1].indexOf(val) >= 0 },
            pattern: /bool|true|0|1/
        });
    }])
    .config(['$compileProvider',
        function ($compileProvider) {
            $compileProvider.aHrefSanitizationWhitelist(/^\s*(https?|ftp|mailto|tel|file|blob):/);
        }])
    .directive('numericOnly', function(){
        return {
            require: 'ngModel',
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
    })
    .directive('numericOnlyMobile', function(){
        return {
            require: 'ngModel',
            link: function(scope, element, attrs, modelCtrl) {

                modelCtrl.$parsers.push(function (inputValue) {
                    var transformedInput = inputValue ? inputValue.replace(/[^\d]/g,'') : null;

                    if (transformedInput!=inputValue) {
                        modelCtrl.$setViewValue(transformedInput);
                        modelCtrl.$render();
                    }
                    return transformedInput;
                });
            }
        };
    }).directive('dynaSrc', function () {
        return {
            restrict: 'A',
            link: function (scope, element, attrs) {
                var current = element;
                scope.$watch(function() { return attrs.dynaSrc; }, function () {
                    var clone = element
                        .clone().attr('src', attrs.dynaSrc);
                    current.replaceWith(clone);
                    current = clone;
                });
            }
        };
    });
