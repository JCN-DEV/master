'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('eduLevel', {
                parent: 'entity',
                url: '/eduLevels',
                data: {
                    authorities: ['ROLE_USER','ROLE_ADMIN'],
                    pageTitle: 'stepApp.eduLevel.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/eduLevel/eduLevels.html',
                        controller: 'EduLevelController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('eduLevel');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('eduLevel.detail', {
                parent: 'entity',
                url: '/eduLevel/{id}',
                data: {
                    authorities: ['ROLE_USER','ROLE_ADMIN'],
                    pageTitle: 'stepApp.eduLevel.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/eduLevel/eduLevel-detail.html',
                        controller: 'EduLevelDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('eduLevel');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'EduLevel', function($stateParams, EduLevel) {
                        return EduLevel.get({id : $stateParams.id});
                    }]
                }
            })
            .state('eduLevel.new', {
                parent: 'eduLevel',
                url: '/new',
                /*data: {
                    authorities: ['ROLE_USER','ROLE_ADMIN'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/eduLevel/eduLevel-dialog.html',
                        controller: 'EduLevelDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    name: null,
                                    description: null,
                                    status: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('eduLevel', null, { reload: true });
                    }, function() {
                        $state.go('eduLevel');
                    })
                }]
            })*/
                data: {
                    authorities: ['ROLE_ADMIN'],
                    pageTitle: 'stepApp.eduLevel.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/eduLevel/eduLevel-dialog.html',
                        controller: 'EduLevelDialogController'
                    }
                },
                resolve: {

                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('eduLevel');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'EduLevel', function($stateParams, EduLevel) {
                        return null;
                    }]
                }
            })
            .state('eduLevel.edit', {
                parent: 'eduLevel',
                url: '/{id}/edit',
                /*data: {
                    authorities: ['ROLE_USER','ROLE_ADMIN'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/eduLevel/eduLevel-dialog.html',
                        controller: 'EduLevelDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['EduLevel', function(EduLevel) {
                                return EduLevel.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('eduLevel', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })*/
                data: {
                    authorities: ['ROLE_ADMIN'],
                    pageTitle: 'stepApp.eduLevel.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/eduLevel/eduLevel-dialog.html',
                        controller: 'EduLevelDialogController'
                    }
                },
                resolve: {

                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('eduLevel');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'EduLevel', function($stateParams, EduLevel) {
                        return EduLevel.get({id : $stateParams.id});
                    }]
                }
            })
            .state('eduLevel.delete', {
                parent: 'eduLevel',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER','ROLE_ADMIN'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/eduLevel/eduLevel-delete-dialog.html',
                        controller: 'EduLevelDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['EduLevel', function(EduLevel) {
                                return EduLevel.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('eduLevel', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
