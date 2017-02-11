'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('prlEconomicalCodeInfo', {
                parent: 'payroll',
                url: '/prlEconomicalCodeInfos',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.prlEconomicalCodeInfo.home.title'
                },
                views: {
                    'payrollManagementView@payroll': {
                        templateUrl: 'scripts/app/entities/payroll/economicalCodeInfo/prlEconomicalCodeInfos.html',
                        controller: 'PrlEconomicalCodeInfoController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('prlEconomicalCodeInfo');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('prlEconomicalCodeInfo.detail', {
                parent: 'payroll',
                url: '/prlEconomicalCodeInfo/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.prlEconomicalCodeInfo.detail.title'
                },
                views: {
                    'payrollManagementView@payroll': {
                        templateUrl: 'scripts/app/entities/payroll/economicalCodeInfo/prlEconomicalCodeInfo-detail.html',
                        controller: 'PrlEconomicalCodeInfoDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('prlEconomicalCodeInfo');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'PrlEconomicalCodeInfo', function($stateParams, PrlEconomicalCodeInfo) {
                        return PrlEconomicalCodeInfo.get({id : $stateParams.id});
                    }]
                }
            })
            .state('prlEconomicalCodeInfo.new', {
                parent: 'prlEconomicalCodeInfo',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                views: {
                    'payrollManagementView@payroll': {
                        templateUrl: 'scripts/app/entities/payroll/economicalCodeInfo/prlEconomicalCodeInfo-dialog.html',
                        controller: 'PrlEconomicalCodeInfoDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('prlEconomicalCodeInfo');
                        return $translate.refresh();
                    }],
                    entity: function () {
                        return {
                            codeType: null,
                            codeName: null,
                            createDate: null,
                            createBy: null,
                            updateDate: null,
                            updateBy: null,
                            id: null
                        };
                    }
                }
            })
            .state('prlEconomicalCodeInfo.edit', {
                parent: 'prlEconomicalCodeInfo',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                views: {
                    'payrollManagementView@payroll': {
                        templateUrl: 'scripts/app/entities/payroll/economicalCodeInfo/prlEconomicalCodeInfo-dialog.html',
                        controller: 'PrlEconomicalCodeInfoDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('prlEconomicalCodeInfo');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'PrlEconomicalCodeInfo', function($stateParams, PrlEconomicalCodeInfo) {
                        return PrlEconomicalCodeInfo.get({id : $stateParams.id});
                    }]
                }
            })
            .state('prlEconomicalCodeInfo.delete', {
                parent: 'prlEconomicalCodeInfo',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/payroll/economicalCodeInfo/prlEconomicalCodeInfo-delete-dialog.html',
                        controller: 'PrlEconomicalCodeInfoDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['PrlEconomicalCodeInfo', function(PrlEconomicalCodeInfo) {
                                return PrlEconomicalCodeInfo.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('prlEconomicalCodeInfo', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
