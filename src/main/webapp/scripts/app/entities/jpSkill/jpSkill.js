'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('jpSkill', {
                parent: 'entity',
                url: '/jpSkills',
                data: {
                    authorities: ['ROLE_USER','ROLE_JOBSEEKER','ROLE_ADMIN'],
                    pageTitle: 'stepApp.jpSkill.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/jpSkill/jpSkills.html',
                        controller: 'JpSkillController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('jpSkill');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('jpSkill.detail', {
                parent: 'entity',
                url: '/jpSkill/{id}',
                data: {
                    authorities: ['ROLE_USER','ROLE_JOBSEEKER','ROLE_ADMIN'],
                    pageTitle: 'stepApp.jpSkill.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/jpSkill/jpSkill-detail.html',
                        controller: 'JpSkillDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('jpSkill');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'JpSkill', function($stateParams, JpSkill) {
                        return JpSkill.get({id : $stateParams.id});
                    }]
                }
            })
            .state('jpSkill.new', {
                parent: 'jpSkill',
                url: '/new',
                /*data: {
                    authorities: ['ROLE_USER','ROLE_JOBSEEKER','ROLE_ADMIN'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/jpSkill/jpSkill-dialog.html',
                        controller: 'JpSkillDialogController',
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
                        $state.go('jpSkill', null, { reload: true });
                    }, function() {
                        $state.go('jpSkill');
                    })
                }]
            })*/
                data: {
                    authorities: ['ROLE_ADMIN'],
                    pageTitle: 'stepApp.jpSkill.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/jpSkill/jpSkill-dialog.html',
                        controller: 'JpSkillDialogController'
                    }
                },
                resolve: {

                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('jpSkill');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'JpSkill', function($stateParams, JpSkill) {
                        return null;
                    }]
                }
            })
            .state('jpSkill.edit', {
                parent: 'jpSkill',
                url: '/{id}/edit',
                /*data: {
                    authorities: ['ROLE_USER','ROLE_JOBSEEKER','ROLE_ADMIN'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/jpSkill/jpSkill-dialog.html',
                        controller: 'JpSkillDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['JpSkill', function(JpSkill) {
                                return JpSkill.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('jpSkill', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })*/
                data: {
                    authorities: ['ROLE_ADMIN'],
                    pageTitle: 'stepApp.jpSkill.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/jpSkill/jpSkill-dialog.html',
                        controller: 'JpSkillDialogController'
                    }
                },
                resolve: {

                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('jpSkill');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'JpSkill', function($stateParams, JpSkill) {
                        return JpSkill.get({id : $stateParams.id});

                    }]
                }
            })
            .state('jpSkill.delete', {
                parent: 'jpSkill',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER','ROLE_JOBSEEKER','ROLE_ADMIN'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/jpSkill/jpSkill-delete-dialog.html',
                        controller: 'JpSkillDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['JpSkill', function(JpSkill) {
                                return JpSkill.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('jpSkill', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
