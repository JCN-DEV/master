'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('cmsCurriculum', {
                parent: 'entity',
                url: '/cmsCurriculums',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.cmsCurriculum.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/cmsCurriculum/cmsCurriculums.html',
                        controller: 'CmsCurriculumController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('cmsCurriculum');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('cmsCurriculum.detail', {
                parent: 'entity',
                url: '/cmsCurriculum/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.cmsCurriculum.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/cmsCurriculum/cmsCurriculum-detail.html',
                        controller: 'CmsCurriculumDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('cmsCurriculum');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'CmsCurriculum', function($stateParams, CmsCurriculum) {
                        return CmsCurriculum.get({id : $stateParams.id});
                    }]
                }
            })
            .state('cmsCurriculum.new', {
                parent: 'cmsCurriculum',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/cmsCurriculum/cmsCurriculum-dialog.html',
                        controller: 'CmsCurriculumDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    code: null,
                                    name: null,
                                    duration: null,
                                    description: null,
                                    status: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('cmsCurriculum', null, { reload: true });
                    }, function() {
                        $state.go('cmsCurriculum');
                    })
                }]
            })
            .state('cmsCurriculum.edit', {
                parent: 'cmsCurriculum',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/cmsCurriculum/cmsCurriculum-dialog.html',
                        controller: 'CmsCurriculumDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['CmsCurriculum', function(CmsCurriculum) {
                                return CmsCurriculum.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('cmsCurriculum', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('cmsCurriculum.delete', {
                parent: 'cmsCurriculum',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/cmsCurriculum/cmsCurriculum-delete-dialog.html',
                        controller: 'CmsCurriculumDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['CmsCurriculum', function(CmsCurriculum) {
                                return CmsCurriculum.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('cmsCurriculum', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
