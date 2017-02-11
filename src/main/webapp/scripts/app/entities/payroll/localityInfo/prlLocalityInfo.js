'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('prlLocalityInfo', {
                parent: 'payroll',
                url: '/prlLocalityInfos',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.prlLocalityInfo.home.title'
                },
                views: {
                    'payrollManagementView@payroll': {
                        templateUrl: 'scripts/app/entities/payroll/localityInfo/prlLocalityInfos.html',
                        controller: 'PrlLocalityInfoController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('prlLocalityInfo');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('prlLocalityInfo.detail', {
                parent: 'payroll',
                url: '/prlLocalityInfo/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.prlLocalityInfo.detail.title'
                },
                views: {
                    'payrollManagementView@payroll': {
                        templateUrl: 'scripts/app/entities/payroll/localityInfo/prlLocalityInfo-detail.html',
                        controller: 'PrlLocalityInfoDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('prlLocalityInfo');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'PrlLocalityInfo', function($stateParams, PrlLocalityInfo) {
                        return PrlLocalityInfo.get({id : $stateParams.id});
                    }]
                }
            })
            .state('prlLocalityInfo.new', {
                parent: 'prlLocalityInfo',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                views: {
                    'payrollManagementView@payroll': {
                        templateUrl: 'scripts/app/entities/payroll/localityInfo/prlLocalityInfo-dialog.html',
                        controller: 'PrlLocalityInfoDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('prlLocalityInfo');
                        return $translate.refresh();
                    }],
                    entity: function () {
                        return {
                            name: null,
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
            .state('prlLocalityInfo.edit', {
                parent: 'prlLocalityInfo',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                views: {
                    'payrollManagementView@payroll': {
                        templateUrl: 'scripts/app/entities/payroll/localityInfo/prlLocalityInfo-dialog.html',
                        controller: 'PrlLocalityInfoDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('prlLocalityInfo');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'PrlLocalityInfo', function($stateParams, PrlLocalityInfo) {
                        return PrlLocalityInfo.get({id : $stateParams.id});
                    }]
                }
            })
            .state('prlLocalityInfo.delete', {
                parent: 'prlLocalityInfo',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/payroll/localityInfo/prlLocalityInfo-delete-dialog.html',
                        controller: 'PrlLocalityInfoDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['PrlLocalityInfo', function(PrlLocalityInfo) {
                                return PrlLocalityInfo.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('prlLocalityInfo', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
