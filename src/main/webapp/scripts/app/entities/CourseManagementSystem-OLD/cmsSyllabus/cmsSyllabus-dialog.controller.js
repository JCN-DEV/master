'use strict';

angular.module('stepApp').controller('CmsSyllabusDialogController',
['$scope', '$rootScope', '$stateParams', '$state', 'entity', 'CmsSyllabus', 'CmsCurriculum', 'CmsSyllabusByNameAndVersion',
       function($scope, $rootScope, $stateParams, $state, entity, CmsSyllabus, CmsCurriculum, CmsSyllabusByNameAndVersion) {

        $scope.message = '';
        $scope.content = '';

        $scope.cmsSyllabus = {};
        $scope.cmsSyllabus.status=true;

        $scope.cmscurriculums = CmsCurriculum.query();
        $scope.load = function() {
            CmsSyllabus.get({id : $stateParams.id}, function(result) {
                $scope.cmsSyllabus = result;
                $scope.showAlert = false;
                if ($scope.cmsSyllabus.syllabus) {
                    var blob = $rootScope.b64toBlob($scope.cmsSyllabus.syllabus, 'application/pdf');
                    $scope.content = (window.URL || window.webkitURL).createObjectURL(blob);
                }
            });
        };

        $scope.load();

        var onSaveSuccess = function (result) {
            $scope.$emit('stepApp:cmsSyllabusUpdate', result);
            /*$modalInstance.close(result);*/
            $scope.isSaving = false;

            $state.go('courseInfo.cmsSyllabus',{},{reload:true});

        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };



         $scope.setSyllabus = function ($file, cmsSyllabus) {
            if ($file) {
                var fileReader = new FileReader();
                fileReader.readAsDataURL($file);
                fileReader.onload = function (e) {
                    var base64Data = e.target.result.substr(e.target.result.indexOf('base64,') + 'base64,'.length);
                    $scope.$apply(function () {
                        cmsSyllabus.syllabus = base64Data;
                        cmsSyllabus.syllabusContentType = $file.type;

                        $scope.fileAdded = "File added";
                        $scope.fileName = $file.name;
                        var blob = $rootScope.b64toBlob(cmsSyllabus.syllabus , 'application/pdf');
                        $scope.content = (window.URL || window.webkitURL).createObjectURL(blob);
                       /* console.log($scope.content);*/
                    });
                };
            }
        };


        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.cmsSyllabus.id != null) {
                CmsSyllabus.update($scope.cmsSyllabus, onSaveSuccess, onSaveError);
            }
            else {
                //CmsSyllabus.save($scope.cmsSyllabus, onSaveSuccess, onSaveError);
                  CmsSyllabusByNameAndVersion.get({cmsCurriculumId: $scope.cmsSyllabus.cmsCurriculum.id, name: $scope.cmsSyllabus.name, version: $scope.cmsSyllabus.version}, function (cmsSyllabus) {
                       console.log('exist');
                       $scope.message = "The Curriculum, Name and Version combination should be unique.";
                          console.log( $scope.message);
                   },
                   function (response) {
                       if (response.status === 404) {
                       console.log('not exist');
                           CmsSyllabus.save($scope.cmsSyllabus, onSaveSuccess, onSaveError);
                           console.log($scope.cmsSyllabus);
                       }
                   }
               );
            }
        };





           $scope.uniqueError = false;
           $scope.checkSyllabusUniqueness = function()
           {
               if ($scope.cmsSyllabus.cmsCurriculum.id != null && $scope.cmsSyllabus.name != null && $scope.cmsSyllabus.version != null) {
                   CmsSyllabusByNameAndVersion.get({
                           cmsCurriculumId: $scope.cmsSyllabus.cmsCurriculum.id,
                           name: $scope.cmsSyllabus.name,
                           version: $scope.cmsSyllabus.version
                       }, function (cmsSyllabus) {
                           console.log('exist');
                           $scope.uniqueError = true;
                       },
                       function (response) {
                           if (response.status === 404) {
                               console.log('not exist');
                               $scope.uniqueError = false;
                               //CmsSubject.save($scope.cmsSubject, onSaveSuccess, onSaveError);
                               //console.log($scope.cmsSubject);
                           }
                       }
                   );
               }
           };





        $scope.clear = function() {
            $scope.cmsSyllabus=null;
        };
}]);
