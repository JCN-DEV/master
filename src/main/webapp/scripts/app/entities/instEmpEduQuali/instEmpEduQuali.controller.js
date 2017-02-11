'use strict';

angular.module('stepApp')
    .controller('InstEmpEduQualiController',
    ['$scope','$state','$modal','DataUtils','InstEmpEduQuali','InstEmpEduQualiSearch','ParseLinks',
    function ($scope, $state, $modal, DataUtils, InstEmpEduQuali, InstEmpEduQualiSearch, ParseLinks) {

        $scope.instEmpEduQualis = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            InstEmpEduQuali.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.instEmpEduQualis = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();


        $scope.search = function () {
            InstEmpEduQualiSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.instEmpEduQualis = result;
            }, function(response) {
                if(response.status === 404) {
                    $scope.loadAll();
                }
            });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.instEmpEduQuali = {
                certificateName: null,
                board: null,
                session: null,
                semester: null,
                rollNo: null,
                passingYear: null,
                cgpa: null,
                certificateCopy: null,
                certificateCopyContentType: null,
                status: null,
                groupSubject: null,
                resultPublishDate: null,
                id: null
            };
        };

        $scope.abbreviate = DataUtils.abbreviate;

        $scope.byteSize = DataUtils.byteSize;
    }]);
