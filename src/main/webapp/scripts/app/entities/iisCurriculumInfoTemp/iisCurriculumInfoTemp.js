'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('iisCurriculumInfoTemp', {
                parent: 'entity',
                url: '/iisCurriculumInfoTemps',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.iisCurriculumInfoTemp.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/iisCurriculumInfoTemp/iisCurriculumInfoTemps.html',
                        controller: 'IisCurriculumInfoTempController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('iisCurriculumInfoTemp');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('iisCurriculumInfoTemp.detail', {
                parent: 'entity',
                url: '/iisCurriculumInfoTemp/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.iisCurriculumInfoTemp.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/iisCurriculumInfoTemp/iisCurriculumInfoTemp-detail.html',
                        controller: 'IisCurriculumInfoTempDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('iisCurriculumInfoTemp');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'IisCurriculumInfoTemp', function($stateParams, IisCurriculumInfoTemp) {
                        return IisCurriculumInfoTemp.get({id : $stateParams.id});
                    }]
                }
            })
            .state('iisCurriculumInfoTemp.new', {
                parent: 'iisCurriculumInfoTemp',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/iisCurriculumInfoTemp/iisCurriculumInfoTemp-dialog.html',
                        controller: 'IisCurriculumInfoTempDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    firstDate: null,
                                    lastDate: null,
                                    mpoEnlisted: null,
                                    recNo: null,
                                    mpoDate: null,
                                    createDate: null,
                                    updateDate: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('iisCurriculumInfoTemp', null, { reload: true });
                    }, function() {
                        $state.go('iisCurriculumInfoTemp');
                    })
                }]
            })
            .state('iisCurriculumInfoTemp.edit', {
                parent: 'iisCurriculumInfoTemp',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/iisCurriculumInfoTemp/iisCurriculumInfoTemp-dialog.html',
                        controller: 'IisCurriculumInfoTempDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['IisCurriculumInfoTemp', function(IisCurriculumInfoTemp) {
                                return IisCurriculumInfoTemp.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('iisCurriculumInfoTemp', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('iisCurriculumInfoTemp.delete', {
                parent: 'iisCurriculumInfoTemp',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/iisCurriculumInfoTemp/iisCurriculumInfoTemp-delete-dialog.html',
                        controller: 'IisCurriculumInfoTempDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['IisCurriculumInfoTemp', function(IisCurriculumInfoTemp) {
                                return IisCurriculumInfoTemp.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('iisCurriculumInfoTemp', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
