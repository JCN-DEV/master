'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('prlLocalitySetInfo', {
                parent: 'payroll',
                url: '/prlLocalitySetInfos',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.prlLocalitySetInfo.home.title'
                },
                views: {
                    'payrollManagementView@payroll': {
                        templateUrl: 'scripts/app/entities/payroll/localitySetInfo/prlLocalitySetInfos.html',
                        controller: 'PrlLocalitySetInfoController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('prlLocalitySetInfo');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('prlLocalitySetInfo.detail', {
                parent: 'payroll',
                url: '/prlLocalitySetInfo/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.prlLocalitySetInfo.detail.title'
                },
                views: {
                    'payrollManagementView@payroll': {
                        templateUrl: 'scripts/app/entities/payroll/localitySetInfo/prlLocalitySetInfo-detail.html',
                        controller: 'PrlLocalitySetInfoDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('prlLocalitySetInfo');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'PrlLocalitySetInfo', function($stateParams, PrlLocalitySetInfo) {
                        return PrlLocalitySetInfo.get({id : $stateParams.id});
                    }]
                }
            })
            .state('prlLocalitySetInfo.new', {
                parent: 'prlLocalitySetInfo',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                views: {
                    'payrollManagementView@payroll': {
                        templateUrl: 'scripts/app/entities/payroll/localitySetInfo/prlLocalitySetInfo-dialog.html',
                        controller: 'PrlLocalitySetInfoDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('prlLocalitySetInfo');
                        return $translate.refresh();
                    }],
                    entity: function () {
                        return {
                            name: null,
                            fixedSetType: true,
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
            .state('prlLocalitySetInfo.edit', {
                parent: 'prlLocalitySetInfo',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                views: {
                    'payrollManagementView@payroll': {
                        templateUrl: 'scripts/app/entities/payroll/localitySetInfo/prlLocalitySetInfo-dialog.html',
                        controller: 'PrlLocalitySetInfoDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('prlLocalitySetInfo');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'PrlLocalitySetInfo', function($stateParams, PrlLocalitySetInfo) {
                        return PrlLocalitySetInfo.get({id : $stateParams.id});
                    }]
                }
            })
            .state('prlLocalitySetInfo.delete', {
                parent: 'prlLocalitySetInfo',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/payroll/localitySetInfo/prlLocalitySetInfo-delete-dialog.html',
                        controller: 'PrlLocalitySetInfoDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['PrlLocalitySetInfo', function(PrlLocalitySetInfo) {
                                return PrlLocalitySetInfo.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('prlLocalitySetInfo', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
