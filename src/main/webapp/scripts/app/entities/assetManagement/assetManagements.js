'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('assetManagements', {
                parent: 'entity',
                url: '/asset-Management',
                data: {
                    authorities: [],
                    pageTitle: 'stepApp.instGenInfo.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/assetManagement/assetManagement.html',
                        controller: 'assetManagementController'
                    },
                    'assetManagementView@assetManagements':{
                        templateUrl: 'scripts/app/entities/assetManagement/dashboard.html',
                        controller: 'assetManagementController'
                    }
                }
            })
    });
