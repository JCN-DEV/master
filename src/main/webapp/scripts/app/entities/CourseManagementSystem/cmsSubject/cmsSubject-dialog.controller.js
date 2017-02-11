'use strict';

angular.module('stepApp').controller('CmsSubjectDialogController',
    ['$scope', '$stateParams', '$state', 'entity', 'CmsSubject', 'CmsCurriculum', 'CmsSyllabus', 'CmsSubByNameAndSyllabusAndCode','CmsSyllabusByCurriculum',
          function($scope, $stateParams,$state, entity, CmsSubject, CmsCurriculum, CmsSyllabus, CmsSubByNameAndSyllabusAndCode,CmsSyllabusByCurriculum) {

        $scope.message = '';
        $scope.cmsSubject = entity;
              $scope.cmsSubject.status=true;

        $scope.cmscurriculums = CmsCurriculum.query({size:100});
        //$scope.cmssyllabuss = CmsSyllabus.query({size:100});
        $scope.load = function(id) {
            CmsSubject.get({id : id}, function(result) {
                $scope.cmsSubject = result;
            });
        };
     /*$scope.cmssyllabussChange=function($data){

     }*/

      $scope.loadSyllabusByCurriculum = function(curriculum){
                     CmsSyllabusByCurriculum.query({id:curriculum.id}, function(result) {
                       console.log("curriculum id");
                       console.log(curriculum.id);
                           $scope.cmsSyllabuss = result;
                       });
              };


        var onSaveSuccess = function (result) {
            $scope.$emit('stepApp:cmsSubjectUpdate', result);
           /* $modalInstance.close(result);*/
            $scope.isSaving = false;
            $state.go('courseInfo.cmsSubject',{},{reload:true});


        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };


         /* $scope.filterSyllabusByStatus = function (item) {
                  return function (item) {
                      if (item.CmsSyllabus.status == 'TRUE')
                      {
                          return true;
                      }
                      return false;
                  };
              };*/

              $scope.filterActiveSyllabus = function () {
                  return function (item) {
                      if (item.status == true)
                      {
                          return true;
                      }
                      return false;
                  };
              };


              $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.cmsSubject.id != null) {
                CmsSubject.update($scope.cmsSubject, onSaveSuccess, onSaveError);
            } /*else {
                //CmsSubject.save($scope.cmsSubject, onSaveSuccess, onSaveError);
                  CmsSubjectByCode.get({code: $scope.cmsSubject.code}, function (cmsSubject) {
                       console.log('exist');
                       $scope.message = "This Code is already existed.";
                   },
                   function (response) {
                       if (response.status === 404) {
                       console.log('not exist');
                           CmsSubject.save($scope.cmsSubject, onSaveSuccess, onSaveError);
                           console.log($scope.cmsSubject);
                       }
                   }
               );
            }*/
            else {
                //CmsSyllabus.save($scope.cmsSyllabus, onSaveSuccess, onSaveError);
                CmsSubByNameAndSyllabusAndCode.get({cmsSyllabusId: $scope.cmsSubject.cmsSyllabus.id,code: $scope.cmsSubject.code, name: $scope.cmsSubject.name}, function (cmsSubject) {
                        console.log('exist');
                        $scope.message = "The Syllabus, Subject Code and Subject name combination should be unique.";
                        console.log( $scope.message);
                    },
                    function (response) {
                        if (response.status === 404) {
                            console.log('not exist');
                            CmsSubject.save($scope.cmsSubject, onSaveSuccess, onSaveError);
                            console.log($scope.cmsSubject);
                        }
                    }
                );

            }
        };

        $scope.uniqueError = false;
        $scope.checkCompositeUniqueness = function()
        {
            if ($scope.cmsSubject.cmsSyllabus.id != null && $scope.cmsSubject.code != null && $scope.cmsSubject.name != null) {
                CmsSubByNameAndSyllabusAndCode.get({
                        cmsSyllabusId: $scope.cmsSubject.cmsSyllabus.id,
                        code: $scope.cmsSubject.code,
                        name: $scope.cmsSubject.name
                    }, function (cmsSubject) {
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
           $scope.cmsSubject=null;
        };
}]);
