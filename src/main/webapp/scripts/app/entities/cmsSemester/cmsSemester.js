'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('cmsSemester', {
                parent: 'entity',
                url: '/cmsSemesters',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.cmsSemester.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/cmsSemester/cmsSemesters.html',
                        controller: 'CmsSemesterController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('cmsSemester');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('cmsSemester.detail', {
                parent: 'entity',
                url: '/cmsSemester/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.cmsSemester.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/cmsSemester/cmsSemester-detail.html',
                        controller: 'CmsSemesterDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('cmsSemester');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'CmsSemester', function($stateParams, CmsSemester) {
                        return CmsSemester.get({id : $stateParams.id});
                    }]
                }
            })
            .state('cmsSemester.new', {
                parent: 'cmsSemester',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/cmsSemester/cmsSemester-dialog.html',
                        controller: 'CmsSemesterDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    code: null,
                                    name: null,
                                    year: null,
                                    duration: null,
                                    description: null,
                                    status: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('cmsSemester', null, { reload: true });
                    }, function() {
                        $state.go('cmsSemester');
                    })
                }]
            })
            .state('cmsSemester.edit', {
                parent: 'cmsSemester',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/cmsSemester/cmsSemester-dialog.html',
                        controller: 'CmsSemesterDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['CmsSemester', function(CmsSemester) {
                                return CmsSemester.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('cmsSemester', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('cmsSemester.delete', {
                parent: 'cmsSemester',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/cmsSemester/cmsSemester-delete-dialog.html',
                        controller: 'CmsSemesterDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['CmsSemester', function(CmsSemester) {
                                return CmsSemester.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('cmsSemester', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
