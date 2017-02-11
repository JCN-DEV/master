'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('vehicle', {
                parent: 'entity',
                url: '/vehicle',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.vclVehicle.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/vehicle/vehicle.html',
                        controller: 'vehicleHomeController'
                    },
                   'vehicleManagementView@vehicle':{
                        templateUrl: 'scripts/app/entities/vehicle/vehicle-dashboard.html',
                        controller: 'vehicleHomeController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('vclVehicle');
                        $translatePartialLoader.addPart('vehicleTypes');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
    });
