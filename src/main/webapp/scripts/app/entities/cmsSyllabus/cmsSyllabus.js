'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('cmsSyllabus', {
                parent: 'entity',
                url: '/cmsSyllabuss',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.cmsSyllabus.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/cmsSyllabus/cmsSyllabuss.html',
                        controller: 'CmsSyllabusController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('cmsSyllabus');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('cmsSyllabus.detail', {
                parent: 'entity',
                url: '/cmsSyllabus/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.cmsSyllabus.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/cmsSyllabus/cmsSyllabus-detail.html',
                        controller: 'CmsSyllabusDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('cmsSyllabus');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'CmsSyllabus', function($stateParams, CmsSyllabus) {
                        return CmsSyllabus.get({id : $stateParams.id});
                    }]
                }
            })
            .state('cmsSyllabus.new', {
                parent: 'cmsSyllabus',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/cmsSyllabus/cmsSyllabus-dialog.html',
                        controller: 'CmsSyllabusDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    version: null,
                                    name: null,
                                    description: null,
                                    status: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('cmsSyllabus', null, { reload: true });
                    }, function() {
                        $state.go('cmsSyllabus');
                    })
                }]
            })
            .state('cmsSyllabus.edit', {
                parent: 'cmsSyllabus',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/cmsSyllabus/cmsSyllabus-dialog.html',
                        controller: 'CmsSyllabusDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['CmsSyllabus', function(CmsSyllabus) {
                                return CmsSyllabus.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('cmsSyllabus', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('cmsSyllabus.delete', {
                parent: 'cmsSyllabus',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/cmsSyllabus/cmsSyllabus-delete-dialog.html',
                        controller: 'CmsSyllabusDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['CmsSyllabus', function(CmsSyllabus) {
                                return CmsSyllabus.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('cmsSyllabus', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
