'use strict';

angular.module('stepApp').controller('DlContUpldDialogController',

    ['$scope', '$rootScope', '$state', '$q', '$stateParams', 'entity', 'ParseLinks', 'DlContUpld', 'DlContTypeSet', 'DlContCatSet', 'DlContSCatSet', 'InstEmployee', 'DlFileType', 'ContUpldByCode', 'Principal', 'findOneByIsbn', 'FindActiveTypes', 'FindActivcategory', 'FindActiveSubcategory', 'DlSourceSetUp','FindActiveFileType','FindActiveSourceSetup',
        function ($scope, $rootScope, $state, $q, $stateParams, entity, ParseLinks, DlContUpld, DlContTypeSet, DlContCatSet, DlContSCatSet, InstEmployee, DlFileType, ContUpldByCode, Principal, findOneByIsbn, FindActiveTypes, FindActivcategory, FindActiveSubcategory, DlSourceSetUp,FindActiveFileType,FindActiveSourceSetup) {

            $scope.dlContUpld = entity;

            /*
             $scope.dlContTypeSets = DlContTypeSet.query();
             */
            $scope.dlContTypeSets = FindActiveTypes.query();

            $scope.dlfiletypes = FindActiveFileType.query();
            $scope.dlSourceSetUps = FindActiveSourceSetup.query();


            ContUpldByCode.get({code: $scope.dlContUpld.code}, function (dlContUpld) {
                $scope.message = "The  Code is already existed.";
            });
            var roles = $rootScope.userRoles;
            /* var a = roles.indexOf("ROLE_INSTEMP");
             //var b = roles.search("ROLE_ADMIN");
             console.log(a);
             //console.log(b);*/
            /*if(roles.indexOf("ROLE_INSTEMP") != -1){
             console.log("Employee Role");
             }
             if(roles.indexOf("ROLE_ADMIN") != -1){
             console.log("Admin Role");
             }*/
            findOneByIsbn.get({name: $scope.dlContUpld.isbnNo}, function (dlContUpld) {

                $scope.message = "The  File Type is already exist.";

            });


            $scope.load = function (id) {
                DlContUpld.get({id: id}, function (result) {
                    $scope.dlContUpld = result;
                });
            };

            var onSaveSuccess = function (result) {
                $scope.$emit('stepApp:dlContUpldUpdate', result);
                $scope.isSaving = false;
                $state.go('libraryInfo.dlContUpld', {}, {reload: true});
            };

            var onSaveError = function (result) {
                $scope.isSaving = false;
            };


            $q.all([entity.$promise]).then(function () {
                if (entity.dlContSCatSet) {
                    $scope.dlContSCatSet = entity.dlContSCatSet.name;
                    if (entity.dlContSCatSet.dlContCatSet) {
                        $scope.dlContCatSet = entity.dlContSCatSet.dlContCatSet.name;
                        if (entity.dlContSCatSet.dlContCatSet.dlContTypeSet) {
                            $scope.dlContTypeSet = entity.dlContSCatSet.dlContCatSet.dlContTypeSet.name;
                        }
                        else {
                            $scope.dlContTypeSet = "Select Content Type"
                        }
                    }
                    else {
                        $scope.dlContCatSet = "Select Category";
                        $scope.dlContTypeSet = "Select Content Type"
                    }
                }
                else {
                    $scope.dlContTypeSet = "Select Content Type"
                    $scope.dlContCatSet = "Select Category";
                    $scope.dlContSCatSet = "Select Sub Category";
                }
            });

            $scope.dlContTypeSets = FindActiveTypes.query();
            var allDlContCatSet = FindActivcategory.query({page: $scope.page, size: 65}, function (result, headers) {
                return result;
            });
            var allDlContSCatSet = FindActiveSubcategory.query({
                page: $scope.page,
                size: 500
            }, function (result, headers) {
                return result;
            });

            $scope.updatedDlContSCatSet = function (select) {
                /* console.log("selected district .............");
                 console.log(select);*/
                $scope.dlContSCatSets = [];
                angular.forEach(allDlContSCatSet, function (dlContSCatSet) {
                    if (select.id == dlContSCatSet.dlContCatSet.id) {
                        $scope.dlContSCatSets.push(dlContSCatSet);
                    }
                });

            };

            $scope.dlContCatSets = FindActivcategory.query();
            $scope.dlContSCatSets = FindActiveSubcategory.query();

            $scope.updatedDlContCatSet = function (select) {
                $scope.dlContCatSets = [];
                angular.forEach(allDlContCatSet, function (dlContCatSet) {

                    if ((dlContCatSet.dlContTypeSet && select) && (select.id != dlContCatSet.dlContTypeSet.id)) {
                        console.log("There is error");
                    } else {
                        console.log("There is the fire place");
                        $scope.dlContCatSets.push(dlContCatSet);
                    }
                });
            };


            $scope.save = function () {

                if (roles.indexOf("ROLE_ADMIN") != -1) {
                    console.log("==============is authenticated============");
                    console.log(Principal.isAuthenticated());
                    console.log(Principal.isAuthenticated());
                    console.log("==============is Role============");
                    console.log(Principal.hasAuthority());

                    $scope.isSaving = true;
                    if ($scope.dlContUpld.id != null) {
                        DlContUpld.update($scope.dlContUpld, onSaveSuccess, onSaveError);
                        $rootScope.setSuccessMessage('stepApp.dlContUpld.updated');
                    } else {
                        $scope.dlContUpld.status = 3;
                        DlContUpld.save($scope.dlContUpld, onSaveSuccess, onSaveError);
                        $rootScope.setWarningMessage('stepApp.dlContUpld.created');
                    }
                }
                else {
                    console.log("==============Else is authenticated============");
                    $scope.isSaving = true;
                    if ($scope.dlContUpld.id != null) {
                        DlContUpld.update($scope.dlContUpld, onSaveSuccess, onSaveError);
                        $rootScope.setSuccessMessage('stepApp.dlContUpld.updated');
                    }
                    else {
                        $scope.dlContUpld.status = 2;
                        DlContUpld.save($scope.dlContUpld, onSaveSuccess, onSaveError);
                        $rootScope.setWarningMessage('stepApp.dlContUpld.created');
                    }
                }


            };
            $scope.allowedTypes = '';
            $scope.filetype = function (dlFileType) {

                $scope.appId = dlFileType;
                console.log($scope.appId);

                if (dlFileType.fileType == '.jpg') {
                    $scope.allowedTypes = '.jpg'
                }
                else if (dlFileType.fileType == '.pdf') {
                    $scope.allowedTypes = '.pdf'
                }
                else if (dlFileType.fileType == '.doc') {
                    $scope.allowedTypes = '.doc'
                }
                else if (dlFileType.fileType == '.mp4') {
                    $scope.allowedTypes = '.mp4'
                }
                else if (dlFileType.fileType == '.mp3') {
                    $scope.allowedTypes = '.mp3'
                }
                else if (dlFileType.fileType == '.docx') {
                    $scope.allowedTypes = '.docx'
                }
                else if (dlFileType.fileType == '.png') {
                    $scope.allowedTypes = '.png'
                }
                else if (dlFileType.fileType == '.gif') {
                    $scope.allowedTypes = '.gif'
                }
                else if (dlFileType.fileType == '.csv') {
                    $scope.allowedTypes = '.csv'
                }
                else if (dlFileType.fileType == '.ppt') {
                    $scope.allowedTypes = '.ppt'
                }


            }

            $scope.setContent = function ($file, dlContUpld) {
                console.log($scope.appId);
                if (!$file) {
                }
                if (($file.size / 1024) / 1024 > $scope.appId.limitMb) {
                    alert(
                        'Your file size is greater than' + $scope.appId.limitMb + "mb Please, select image less than" + $scope.appId.limitMb + " mb.\n\n"
                        + '▬▬▬▬▬▬▬▬▬ஜ۩۞۩ஜ▬▬▬▬▬▬▬▬▬\n\n'
                    );
                    //$state.go('libraryInfo.dlContUpld.new.alert',{},{reload:false});
                    dlContUpld.contentContentType = null;
                    dlContUpld.contentName = null;
                }
                else {
                    var fileReader = new FileReader();
                    fileReader.readAsDataURL($file);
                    fileReader.onload = function (e) {
                        var base64Data = e.target.result.substr(e.target.result.indexOf('base64,') + 'base64,'.length);
                        $scope.$apply(function () {
                            dlContUpld.content = base64Data;
                            dlContUpld.contentContentType = $file.type;
                            dlContUpld.contentName = $file.name;
                        });
                    };
                }
            };

            $scope.setContentImage = function ($file, dlContUpld) {
                if ($file) {
                    var fileReader = new FileReader();
                    fileReader.readAsDataURL($file);
                    fileReader.onload = function (e) {
                        var base64Data = e.target.result.substr(e.target.result.indexOf('base64,') + 'base64,'.length);
                        $scope.$apply(function () {
                            dlContUpld.contentImage = base64Data;
                            dlContUpld.contentImageContentType = $file.type;
                            dlContUpld.contentImageName = $file.name;

                        });
                    };
                }

            };
        }]);
