'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('prlHouseRentAllowInfo', {
                parent: 'payroll',
                url: '/prlHouseRentAllowInfos',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.prlHouseRentAllowInfo.home.title'
                },
                views: {
                    'payrollManagementView@payroll': {
                        templateUrl: 'scripts/app/entities/payroll/houseRentAllowInfo/prlHouseRentAllowInfos.html',
                        controller: 'PrlHouseRentAllowInfoController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('prlHouseRentAllowInfo');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('prlHouseRentAllowInfo.detail', {
                parent: 'payroll',
                url: '/prlHouseRentAllowInfo/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.prlHouseRentAllowInfo.detail.title'
                },
                views: {
                    'payrollManagementView@payroll': {
                        templateUrl: 'scripts/app/entities/payroll/houseRentAllowInfo/prlHouseRentAllowInfo-detail.html',
                        controller: 'PrlHouseRentAllowInfoDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('prlHouseRentAllowInfo');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'PrlHouseRentAllowInfo', function($stateParams, PrlHouseRentAllowInfo) {
                        return PrlHouseRentAllowInfo.get({id : $stateParams.id});
                    }]
                }
            })
            .state('prlHouseRentAllowInfo.new', {
                parent: 'prlHouseRentAllowInfo',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                views: {
                    'payrollManagementView@payroll': {
                        templateUrl: 'scripts/app/entities/payroll/houseRentAllowInfo/prlHouseRentAllowInfo-dialog.html',
                        controller: 'PrlHouseRentAllowInfoDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('prlHouseRentAllowInfo');
                        return $translate.refresh();
                    }],
                    entity: function () {
                        return {
                            name: null,
                            basicSalaryMin: null,
                            basicSalaryMax: null,
                            minimumHouseRent: null,
                            rentPercentage: null,
                            activeStatus: true,
                            createDate: null,
                            createBy: null,
                            updateDate: null,
                            updateBy: null,
                            id: null
                        };
                    }
                }
            })
            .state('prlHouseRentAllowInfo.edit', {
                parent: 'prlHouseRentAllowInfo',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                views: {
                    'payrollManagementView@payroll': {
                        templateUrl: 'scripts/app/entities/payroll/houseRentAllowInfo/prlHouseRentAllowInfo-dialog.html',
                        controller: 'PrlHouseRentAllowInfoDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('prlHouseRentAllowInfo');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'PrlHouseRentAllowInfo', function($stateParams, PrlHouseRentAllowInfo) {
                        return PrlHouseRentAllowInfo.get({id : $stateParams.id});
                    }]
                }
            })
            .state('prlHouseRentAllowInfo.delete', {
                parent: 'prlHouseRentAllowInfo',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/payroll/houseRentAllowInfo/prlHouseRentAllowInfo-delete-dialog.html',
                        controller: 'PrlHouseRentAllowInfoDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['PrlHouseRentAllowInfo', function(PrlHouseRentAllowInfo) {
                                return PrlHouseRentAllowInfo.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('prlHouseRentAllowInfo', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
