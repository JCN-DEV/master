'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('instAcaInfoTemp', {
                parent: 'entity',
                url: '/instAcaInfoTemps',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.instAcaInfoTemp.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/instAcaInfoTemp/instAcaInfoTemps.html',
                        controller: 'InstAcaInfoTempController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('instAcaInfoTemp');
                        $translatePartialLoader.addPart('curriculum');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('instAcaInfoTemp.detail', {
                parent: 'entity',
                url: '/instAcaInfoTemp/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.instAcaInfoTemp.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/instAcaInfoTemp/instAcaInfoTemp-detail.html',
                        controller: 'InstAcaInfoTempDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('instAcaInfoTemp');
                        $translatePartialLoader.addPart('curriculum');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'InstAcaInfoTemp', function($stateParams, InstAcaInfoTemp) {
                        return InstAcaInfoTemp.get({id : $stateParams.id});
                    }]
                }
            })
            .state('instAcaInfoTemp.new', {
                parent: 'instAcaInfoTemp',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/instAcaInfoTemp/instAcaInfoTemp-dialog.html',
                        controller: 'InstAcaInfoTempDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    academicCounselorName: null,
                                    Mobile: null,
                                    curriculum: null,
                                    totalTradeTechNo: null,
                                    tradeTechDetails: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('instAcaInfoTemp', null, { reload: true });
                    }, function() {
                        $state.go('instAcaInfoTemp');
                    })
                }]
            })
            .state('instAcaInfoTemp.edit', {
                parent: 'instAcaInfoTemp',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/instAcaInfoTemp/instAcaInfoTemp-dialog.html',
                        controller: 'InstAcaInfoTempDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['InstAcaInfoTemp', function(InstAcaInfoTemp) {
                                return InstAcaInfoTemp.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('instAcaInfoTemp', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
