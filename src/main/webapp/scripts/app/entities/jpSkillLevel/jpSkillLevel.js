'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('jpSkillLevel', {
                parent: 'entity',
                url: '/jpSkillLevels',
                data: {
                    authorities: ['ROLE_USER','ROLE_JOBSEEKER','ROLE_ADMIN'],
                    pageTitle: 'stepApp.jpSkillLevel.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/jpSkillLevel/jpSkillLevels.html',
                        controller: 'JpSkillLevelController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('jpSkillLevel');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('jpSkillLevel.detail', {
                parent: 'entity',
                url: '/jpSkillLevel/{id}',
                data: {
                    authorities: ['ROLE_USER','ROLE_JOBSEEKER','ROLE_ADMIN'],
                    pageTitle: 'stepApp.jpSkillLevel.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/jpSkillLevel/jpSkillLevel-detail.html',
                        controller: 'JpSkillLevelDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('jpSkillLevel');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'JpSkillLevel', function($stateParams, JpSkillLevel) {
                        return JpSkillLevel.get({id : $stateParams.id});
                    }]
                }
            })
            .state('jpSkillLevel.new', {
                parent: 'jpSkillLevel',
                url: '/new',
                /*data: {
                    authorities: ['ROLE_USER','ROLE_JOBSEEKER','ROLE_ADMIN'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/jpSkillLevel/jpSkillLevel-dialog.html',
                        controller: 'JpSkillLevelDialogController',
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
                        $state.go('jpSkillLevel', null, { reload: true });
                    }, function() {
                        $state.go('jpSkillLevel');
                    })
                }]
            })*/
                data: {
                    authorities: ['ROLE_ADMIN'],
                    pageTitle: 'stepApp.jpSkillLevel.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/jpSkillLevel/jpSkillLevel-dialog.html',
                        controller: 'JpSkillLevelDialogController'
                    }
                },
                resolve: {

                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('jpSkillLevel');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'JpSkillLevel', function($stateParams, JpSkillLevel) {
                        return null;
                    }]
                }
            })
            .state('jpSkillLevel.edit', {
                parent: 'jpSkillLevel',
                url: '/{id}/edit',
                /*data: {
                    authorities: ['ROLE_USER','ROLE_JOBSEEKER','ROLE_ADMIN'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/jpSkillLevel/jpSkillLevel-dialog.html',
                        controller: 'JpSkillLevelDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['JpSkillLevel', function(JpSkillLevel) {
                                return JpSkillLevel.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('jpSkillLevel', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })*/
                data: {
                    authorities: ['ROLE_ADMIN'],
                    pageTitle: 'stepApp.jpSkillLevel.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/jpSkillLevel/jpSkillLevel-dialog.html',
                        controller: 'JpSkillLevelDialogController'
                    }
                },
                resolve: {

                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('jpSkillLevel');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'JpSkillLevel', function($stateParams, JpSkillLevel) {
                        return JpSkillLevel.get({id : $stateParams.id});
                    }]
                }
            })
            .state('jpSkillLevel.delete', {
                parent: 'jpSkillLevel',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER','ROLE_JOBSEEKER','ROLE_ADMIN'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/jpSkillLevel/jpSkillLevel-delete-dialog.html',
                        controller: 'JpSkillLevelDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['JpSkillLevel', function(JpSkillLevel) {
                                return JpSkillLevel.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('jpSkillLevel', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
