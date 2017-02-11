'use strict';

angular.module('stepApp')
    .controller('InstituteController',
    ['$scope','$state','$modal','DataUtils','Institute','InstituteSearch','ParseLinks',
    function ($scope, $state, $modal, DataUtils, Institute, InstituteSearch, ParseLinks) {

        $scope.institutes = [];
        $scope.page = 0;
        $scope.total = 0;

        $scope.loadAll = function () {
            Institute.query({page: $scope.page, size: 20}, function (result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.institutes = result;
                $scope.total = headers('x-total-count');
            });
        };

        $scope.loadPage = function (page) {
            $scope.page = page;
            $scope.loadAll();
        };

        $scope.loadAll();

        $scope.search = function () {
            InstituteSearch.query({query: $scope.searchQuery}, function (result) {
                $scope.institutes = result;
            }, function (response) {
                if (response.status === 404) {
                    $scope.loadAll();
                }
            });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.institute = {
                name: null,
                dateOfEstablishment: null,
                mpoCode: null,
                postOffice: null,
                location: null,
                isJoint: null,
                multiEducational: null,
                firstApprovedEducationalYear: null,
                lastApprovedEducationalYear: null,
                firstMpoIncludeDate: null,
                lastMpoExpireDate: null,
                nameOfTradeSubject: null,
                lastApprovedSignatureDate: null,
                lastCommitteeApprovedDate: null,
                lastCommitteeApprovedFile: null,
                lastCommitteeApprovedFileContentType: null,
                lastCommitteeExpDate: null,
                lastCommittee1stMeetingFile: null,
                lastCommittee1stMeetingFileContentType: null,
                lastCommitteeExpireDate: null,
                lastMpoMemorialDate: null,
                totalStudent: null,
                lengthOfLibrary: null,
                widthOfLibrary: null,
                numberOfBook: null,
                lastMpoIncludeExpireDate: null,
                numberOfLab: null,
                labArea: null,
                code: null,
                landPhone: null,
                mobile: null,
                email: null,
                constituencyArea: null,
                adminCounselorName: null,
                counselorMobileNo: null,
                insHeadName: null,
                insHeadMobileNo: null,
                deoName: null,
                deoMobileNo: null,
                type: null,
                id: null
            };
        };

        $scope.abbreviate = DataUtils.abbreviate;

        $scope.byteSize = DataUtils.byteSize;

        // bulk operations start
        $scope.areAllInstitutesSelected = false;

        $scope.updateInstitutesSelection = function (instituteArray, selectionValue) {
            for (var i = 0; i < instituteArray.length; i++) {
                instituteArray[i].isSelected = selectionValue;
            }
        };

        $scope.updateStatus = function(id, status){
        	Institute.get({id: id}, function(result){
        		result.status = status;
        		Institute.update(result);
        	});
        };

        $scope.import = function () {
            for (var i = 0; i < $scope.institutes.length; i++) {
                var institute = $scope.institutes[i];
                if (institute.isSelected) {
                    //Institute.update(institute);
                    //TODO: handle bulk export
                }
            }
        };

        $scope.export = function () {
            for (var i = 0; i < $scope.institutes.length; i++) {
                var institute = $scope.institutes[i];
                if (institute.isSelected) {
                    //Institute.update(institute);
                    //TODO: handle bulk export
                }
            }
        };

        $scope.deleteSelected = function () {
            for (var i = 0; i < $scope.institutes.length; i++) {
                var institute = $scope.institutes[i];
                if (institute.isSelected) {
                    Institute.delete(institute);
                }
            }
        };

        $scope.sync = function () {
            for (var i = 0; i < $scope.institutes.length; i++) {
                var institute = $scope.institutes[i];
                if (institute.isSelected) {
                    Institute.update(institute);
                }
            }
        };

        $scope.order = function (predicate, reverse) {
            $scope.predicate = predicate;
            $scope.reverse = reverse;
            Institute.query({page: $scope.page, size: 20}, function (result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.institutes = result;
                $scope.total = headers('x-total-count');
            });
        };
        // bulk operations end

    }]);
