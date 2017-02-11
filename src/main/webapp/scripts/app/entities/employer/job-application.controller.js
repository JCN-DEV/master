'use strict';

angular.module('stepApp')
    .controller('JobApplicationController',
    ['$scope', '$state','ParseLinks','District', '$stateParams', 'entity', 'JobapplicationJob','JobApplicationsDto', 'Jobapplication', 'JobApplicationsTotalExp','JpEmploymentHistoryFirst','JobApplicationsByJob', 'JobapplicationCvSort',
    function ($scope, $state,ParseLinks,District, $stateParams, entity, JobapplicationJob,JobApplicationsDto, Jobapplication, JobApplicationsTotalExp,JpEmploymentHistoryFirst,JobApplicationsByJob, JobapplicationCvSort) {
       // JobapplicationJob.query({id:})
        //$scope.jobApplications = entity;
         /*JobapplicationJob.query({id:$stateParams.id},function(result){
            $scope.jobApplications = result;
         });*/
        $scope.jobApplications = [];
        $scope.districts = [];
        $scope.cvSearchCriteria = {};
        var x = 0;
        District.query({page: $scope.page, size: 65}, function(result, headers) { $scope.districts= result;});
        $scope.page = 0;
        $scope.loadAll = function() {
            JobApplicationsByJob.get({id:$stateParams.id, page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.jobApplications = result;

                console.log(result);

                angular.forEach($scope.jobApplications, function(value, key){
                    console.log('comes to loop');
                    JpEmploymentHistoryFirst.get({id:value.jpEmployee.id}, function(empHist){
                        console.log("experience found");
                        console.log(empHist.stDate);
                        if(empHist.stDate != null){
                            value.experience = $scope.calculateAge(empHist.stDate);
                            console.log('experience :'+$scope.calculateAge(empHist.stDate));
                        }

                    });
                });
                /*for( var i = 0; i < result.length; i++ ){
                    console.log("index :"+i);
                    //$scope.jobApplications.push(result.jobapplications[i]);


                }*/
            });


        };

        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();
        JobApplicationsDto.query({id:$stateParams.id},function(result){
            //console.log(result);
            $scope.applicationDto = result;
            //$scope.jobApplications =result.jobapplications;



         });

        /*JpEmploymentHistoryFirst.get({id:id}, function(result){
            console.log(result);
        });*/

//       $scope.isNewProfile = false;
//       Employer.get({id: 'my'}, function (result) {
//            $scope.isNewProfile = false;
//            $scope.employer = result;
//            $scope.tempEmployer = result;
//        }, function (response) {
//            if (response.status == 404) {
//                $scope.isNewProfile = true;
//            }
//        });

        $scope.calculateAge = function(birthday) {
           // console.log(birthday+" starting date");
            var ageDifMs = Date.now() - new Date(birthday);
            console.log(new Date(ageDifMs));
            var ageDate = new Date(ageDifMs);
            console.log(ageDate.getUTCFullYear()- 1970);
            console.log(">>>>>>>>>>>>>>>>>.");
            console.log(Math.abs(ageDate.getUTCFullYear() - 1970));
            return Math.abs(ageDate.getUTCFullYear() - 1970);
        };

        $scope.sortCvs = function() {
            $scope.cvSearchCriteria.jobId = $stateParams.id;
            $scope.jobApplications = JobapplicationCvSort.save($scope.cvSearchCriteria);
            /*JobapplicationCvSort.save($scope.cvSearchCriteria, function(result){
                $scope.jobApplications = result;
                console.log($scope.jobApplications);
            });*/

            console.log($scope.jobApplications);

        };

        $scope.getExperience = function(id) {
            var exp = 0;
            JpEmploymentHistoryFirst.get({id:id}, function(result){
                exp = 11;
            });
            return exp;
        };

        $scope.getAge = function getAge(dateString) {
            var today = new Date();
            var birthDate = new Date(dateString);
            var age = today.getFullYear() - birthDate.getFullYear();
            var m = today.getMonth() - birthDate.getMonth();
            if (m < 0 || (m === 0 && today.getDate() < birthDate.getDate())) {
                age--;
            }
            return age;
        }


        $scope.jobApplicationStatus = [
            {'applicantStatus' : 'Awaiting'},
            {'applicantStatus' : 'Shortlisted'},
            {'applicantStatus' : 'Interviewed'},
            {'applicantStatus' : 'Rejected'},
            {'applicantStatus' : 'Selected'},
            {'applicantStatus' : 'Offered'}
         ];

        $scope.displayCv = function(applicaiton) {

            var blob = b64toBlob(applicaiton.cv, applicaiton.cvContentType);
            $scope.url = (window.URL || window.webkitURL).createObjectURL( blob );
            window.open($scope.url);
        }

        $scope.showPic = function(jpEmployee) {
            console.log(jpEmployee.name+' name found');
            var blob = b64toBlob(jpEmployee.picture);
            return (window.URL || window.webkitURL).createObjectURL( blob );

        }

        var onSaveFinished = function (result) {
            $scope.$emit('stepApp:jobapplicationUpdate', result);
        };

        $scope.updateStatus=function (status, id){
                Jobapplication.get({id : id}, function(result) {
                    $scope.jobapplication = result;
                    $scope.jobapplication.applicantStatus = status;
                    Jobapplication.update($scope.jobapplication, onSaveFinished);
                    $state.go('job.applied', {id:$stateParams.id}, { reload: true });

                });
         };

        function b64toBlob(b64Data, contentType, sliceSize) {
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

    }]);
