'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('eduBoard', {
                parent: 'entity',
                url: '/eduBoards',
                data: {
                    authorities: ['ROLE_USER','ROLE_ADMIN'],
                    pageTitle: 'stepApp.eduBoard.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/eduBoard/eduBoards.html',
                        controller: 'EduBoardController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('eduBoard');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('eduBoard.detail', {
                parent: 'entity',
                url: '/eduBoard/{id}',
                data: {
                    authorities: ['ROLE_USER','ROLE_ADMIN'],
                    pageTitle: 'stepApp.eduBoard.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/eduBoard/eduBoard-detail.html',
                        controller: 'EduBoardDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('eduBoard');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'EduBoard', function($stateParams, EduBoard) {
                        return EduBoard.get({id : $stateParams.id});
                    }]
                }
            })
            .state('eduBoard.new', {
                parent: 'eduBoard',
                url: '/new',
                /*data: {
                    authorities: ['ROLE_USER','ROLE_ADMIN'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/eduBoard/eduBoard-dialog.html',
                        controller: 'EduBoardDialogController',
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
                        $state.go('eduBoard', null, { reload: true });
                    }, function() {
                        $state.go('eduBoard');
                    })
                }]
            })*/

                data: {
                    authorities: ['ROLE_ADMIN'],
                    pageTitle: 'stepApp.eduBoard.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/eduBoard/eduBoard-dialog.html',
                        controller: 'EduBoardDialogController'
                    }
                },
                resolve: {

                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('eduBoard');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'EduBoard', function($stateParams, EduBoard) {
                        return null;
                    }]
                }
            })
            .state('eduBoard.edit', {
                parent: 'eduBoard',
                url: '/{id}/edit',
                /*data: {
                    authorities: ['ROLE_USER','ROLE_ADMIN'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/eduBoard/eduBoard-dialog.html',
                        controller: 'EduBoardDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['EduBoard', function(EduBoard) {
                                return EduBoard.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('eduBoard', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })*/
                data: {
                    authorities: ['ROLE_ADMIN'],
                    pageTitle: 'stepApp.eduBoard.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/eduBoard/eduBoard-dialog.html',
                        controller: 'EduBoardDialogController'
                    }
                },
                resolve: {

                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('eduBoard');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'EduBoard', function($stateParams, EduBoard) {
                        return EduBoard.get({id : $stateParams.id});
                    }]
                }
            })
            .state('eduBoard.delete', {
                parent: 'eduBoard',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER','ROLE_ADMIN'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/eduBoard/eduBoard-delete-dialog.html',
                        controller: 'EduBoardDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['EduBoard', function(EduBoard) {
                                return EduBoard.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('eduBoard', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
