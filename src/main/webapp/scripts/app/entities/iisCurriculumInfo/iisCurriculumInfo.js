'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('iisCurriculumInfo', {
                parent: 'entity',
                url: '/iisCurriculumInfos',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.iisCurriculumInfo.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/iisCurriculumInfo/iisCurriculumInfos.html',
                        controller: 'IisCurriculumInfoController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('iisCurriculumInfo');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('iisCurriculumInfo.detail', {
                parent: 'entity',
                url: '/iisCurriculumInfo/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.iisCurriculumInfo.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/iisCurriculumInfo/iisCurriculumInfo-detail.html',
                        controller: 'IisCurriculumInfoDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('iisCurriculumInfo');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'IisCurriculumInfo', function($stateParams, IisCurriculumInfo) {
                        return IisCurriculumInfo.get({id : $stateParams.id});
                    }]
                }
            })
            .state('iisCurriculumInfo.new', {
                parent: 'iisCurriculumInfo',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/iisCurriculumInfo/iisCurriculumInfo-dialog.html',
                        controller: 'IisCurriculumInfoDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    firstDate: null,
                                    lastDate: null,
                                    mpoEnlisted: null,
                                    recNo: null,
                                    mpoDate: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('iisCurriculumInfo', null, { reload: true });
                    }, function() {
                        $state.go('iisCurriculumInfo');
                    })
                }]
            })
            .state('iisCurriculumInfo.edit', {
                parent: 'iisCurriculumInfo',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/iisCurriculumInfo/iisCurriculumInfo-dialog.html',
                        controller: 'IisCurriculumInfoDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['IisCurriculumInfo', function(IisCurriculumInfo) {
                                return IisCurriculumInfo.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('iisCurriculumInfo', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('iisCurriculumInfo.delete', {
                parent: 'iisCurriculumInfo',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/iisCurriculumInfo/iisCurriculumInfo-delete-dialog.html',
                        controller: 'IisCurriculumInfoDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['IisCurriculumInfo', function(IisCurriculumInfo) {
                                return IisCurriculumInfo.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('iisCurriculumInfo', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
