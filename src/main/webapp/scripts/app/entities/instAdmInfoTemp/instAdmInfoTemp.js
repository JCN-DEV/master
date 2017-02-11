'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('instAdmInfoTemp', {
                parent: 'entity',
                url: '/instAdmInfoTemps',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.instAdmInfoTemp.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/instAdmInfoTemp/instAdmInfoTemps.html',
                        controller: 'InstAdmInfoTempController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('instAdmInfoTemp');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('instAdmInfoTemp.detail', {
                parent: 'entity',
                url: '/instAdmInfoTemp/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.instAdmInfoTemp.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/instAdmInfoTemp/instAdmInfoTemp-detail.html',
                        controller: 'InstAdmInfoTempDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('instAdmInfoTemp');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'InstAdmInfoTemp', function($stateParams, InstAdmInfoTemp) {
                        return InstAdmInfoTemp.get({id : $stateParams.id});
                    }]
                }
            })
            .state('instAdmInfoTemp.new', {
                parent: 'instAdmInfoTemp',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/instAdmInfoTemp/instAdmInfoTemp-dialog.html',
                        controller: 'InstAdmInfoTempDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    adminCounselorName: null,
                                    counselorMobileNo: null,
                                    insHeadName: null,
                                    insHeadMobileNo: null,
                                    deoName: null,
                                    deoMobileNo: null,
                                    status: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('instAdmInfoTemp', null, { reload: true });
                    }, function() {
                        $state.go('instAdmInfoTemp');
                    })
                }]
            })
            .state('instAdmInfoTemp.edit', {
                parent: 'instAdmInfoTemp',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/instAdmInfoTemp/instAdmInfoTemp-dialog.html',
                        controller: 'InstAdmInfoTempDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['InstAdmInfoTemp', function(InstAdmInfoTemp) {
                                return InstAdmInfoTemp.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('instAdmInfoTemp', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
