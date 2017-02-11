'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('instituteEmpCount', {
                parent: 'entity',
                url: '/instituteEmpCounts',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.instituteEmpCount.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/instituteEmpCount/instituteEmpCounts.html',
                        controller: 'InstituteEmpCountController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('instituteEmpCount');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('instituteEmpCount.detail', {
                parent: 'entity',
                url: '/instituteEmpCount/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.instituteEmpCount.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/instituteEmpCount/instituteEmpCount-detail.html',
                        controller: 'InstituteEmpCountDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('instituteEmpCount');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'InstituteEmpCount', function($stateParams, InstituteEmpCount) {
                        return InstituteEmpCount.get({id : $stateParams.id});
                    }]
                }
            })
            .state('instituteEmpCount.new', {
                parent: 'instituteEmpCount',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/instituteEmpCount/instituteEmpCount-dialog.html',
                        controller: 'InstituteEmpCountDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    totalMaleTeacher: null,
                                    totalFemaleTeacher: null,
                                    totalMaleEmployee: null,
                                    totalFemaleEmployee: null,
                                    totalGranted: null,
                                    totalEngaged: null,
                                    status: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('instituteEmpCount', null, { reload: true });
                    }, function() {
                        $state.go('instituteEmpCount');
                    })
                }]
            })
            .state('instituteEmpCount.edit', {
                parent: 'instituteEmpCount',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/instituteEmpCount/instituteEmpCount-dialog.html',
                        controller: 'InstituteEmpCountDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['InstituteEmpCount', function(InstituteEmpCount) {
                                return InstituteEmpCount.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('instituteEmpCount', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('instituteEmpCount.delete', {
                parent: 'instituteEmpCount',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/instituteEmpCount/instituteEmpCount-delete-dialog.html',
                        controller: 'InstituteEmpCountDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['InstituteEmpCount', function(InstituteEmpCount) {
                                return InstituteEmpCount.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('instituteEmpCount', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
