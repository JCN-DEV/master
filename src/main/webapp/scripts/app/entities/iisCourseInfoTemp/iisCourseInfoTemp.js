'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('iisCourseInfoTemp', {
                parent: 'entity',
                url: '/iisCourseInfoTemps',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.iisCourseInfoTemp.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/iisCourseInfoTemp/iisCourseInfoTemps.html',
                        controller: 'IisCourseInfoTempController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('iisCourseInfoTemp');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('iisCourseInfoTemp.detail', {
                parent: 'entity',
                url: '/iisCourseInfoTemp/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.iisCourseInfoTemp.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/iisCourseInfoTemp/iisCourseInfoTemp-detail.html',
                        controller: 'IisCourseInfoTempDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('iisCourseInfoTemp');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'IisCourseInfoTemp', function($stateParams, IisCourseInfoTemp) {
                        return IisCourseInfoTemp.get({id : $stateParams.id});
                    }]
                }
            })
            .state('iisCourseInfoTemp.new', {
                parent: 'iisCourseInfoTemp',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/iisCourseInfoTemp/iisCourseInfoTemp-dialog.html',
                        controller: 'IisCourseInfoTempDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    perDateEdu: null,
                                    perDateBteb: null,
                                    mpoEnlisted: null,
                                    dateMpo: null,
                                    seatNo: null,
                                    shift: null,
                                    createDate: null,
                                    updateDate: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('iisCourseInfoTemp', null, { reload: true });
                    }, function() {
                        $state.go('iisCourseInfoTemp');
                    })
                }]
            })
            .state('iisCourseInfoTemp.edit', {
                parent: 'iisCourseInfoTemp',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/iisCourseInfoTemp/iisCourseInfoTemp-dialog.html',
                        controller: 'IisCourseInfoTempDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['IisCourseInfoTemp', function(IisCourseInfoTemp) {
                                return IisCourseInfoTemp.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('iisCourseInfoTemp', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('iisCourseInfoTemp.delete', {
                parent: 'iisCourseInfoTemp',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/iisCourseInfoTemp/iisCourseInfoTemp-delete-dialog.html',
                        controller: 'IisCourseInfoTempDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['IisCourseInfoTemp', function(IisCourseInfoTemp) {
                                return IisCourseInfoTemp.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('iisCourseInfoTemp', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
