'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('instGovernBodyTemp', {
                parent: 'entity',
                url: '/instGovernBodyTemps',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.instGovernBodyTemp.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/instGovernBodyTemp/instGovernBodyTemps.html',
                        controller: 'InstGovernBodyTempController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('instGovernBodyTemp');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('instGovernBodyTemp.detail', {
                parent: 'entity',
                url: '/instGovernBodyTemp/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.instGovernBodyTemp.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/instGovernBodyTemp/instGovernBodyTemp-detail.html',
                        controller: 'InstGovernBodyTempDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('instGovernBodyTemp');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'InstGovernBodyTemp', function($stateParams, InstGovernBodyTemp) {
                        return InstGovernBodyTemp.get({id : $stateParams.id});
                    }]
                }
            })
            .state('instGovernBodyTemp.new', {
                parent: 'instGovernBodyTemp',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/instGovernBodyTemp/instGovernBodyTemp-dialog.html',
                        controller: 'InstGovernBodyTempDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    name: null,
                                    position: null,
                                    mobileNo: null,
                                    status: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('instGovernBodyTemp', null, { reload: true });
                    }, function() {
                        $state.go('instGovernBodyTemp');
                    })
                }]
            })
            .state('instGovernBodyTemp.edit', {
                parent: 'instGovernBodyTemp',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/instGovernBodyTemp/instGovernBodyTemp-dialog.html',
                        controller: 'InstGovernBodyTempDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['InstGovernBodyTemp', function(InstGovernBodyTemp) {
                                return InstGovernBodyTemp.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('instGovernBodyTemp', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
