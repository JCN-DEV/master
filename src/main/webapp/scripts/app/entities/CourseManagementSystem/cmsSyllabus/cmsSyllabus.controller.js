'use strict';

angular.module('stepApp')
    .controller('CmsSyllabusController',
        ['$scope', '$state', '$modal', 'CmsSyllabus', 'syllabusList', 'CmsSyllabusSearch',
        function ($scope, $state, $modal, CmsSyllabus,syllabusList,CmsSyllabusSearch) {

        $scope.cmsSyllabuss = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            syllabusList.query({page: $scope.page, size: 2000}, function(result, headers) {
                $scope.cmsSyllabuss = result;
            });
        };

        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();


        $scope.viewQrCode = function (id) {

            CmsSyllabus.get({id: id}, function(result) {
                $scope.cmsSyllabuss = result;
                //console.log(JSON.stringify(result));
                console.log($scope.cmsSyllabuss.syllabusContent);
                //console.log($scope.cmsSyllabuss.cmsS);
                $('#viewQRCode').modal('show');
            });
            //$scope.qrCodeGateway.qrCodeImageLink = "qr_code_files/QR#1452932869160.png";
            //$('#viewQRCode').modal('show');

        };

        $scope.search = function () {
            CmsSyllabusSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.cmsSyllabuss = result;
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
            $scope.cmsSyllabus = {
                version: null,
                name: null,
                description: null,
                status: null,
                id: null,
                syllabusContentType: null,
                syllabusContent: null
            };
        };
    }]);
