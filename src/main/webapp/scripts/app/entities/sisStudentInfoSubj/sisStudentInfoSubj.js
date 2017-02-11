'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('sisStudentInfoSubj', {
                parent: 'entity',
                url: '/sisStudentInfoSubjs',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.sisStudentInfoSubj.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/sisStudentInfoSubj/sisStudentInfoSubjs.html',
                        controller: 'SisStudentInfoSubjController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('sisStudentInfoSubj');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('sisStudentInfoSubj.detail', {
                parent: 'entity',
                url: '/sisStudentInfoSubj/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.sisStudentInfoSubj.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/sisStudentInfoSubj/sisStudentInfoSubj-detail.html',
                        controller: 'SisStudentInfoSubjDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('sisStudentInfoSubj');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'SisStudentInfoSubj', function($stateParams, SisStudentInfoSubj) {
                        return SisStudentInfoSubj.get({id : $stateParams.id});
                    }]
                }
            })
            .state('sisStudentInfoSubj.new', {
                parent: 'sisStudentInfoSubj',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/sisStudentInfoSubj/sisStudentInfoSubj-dialog.html',
                        controller: 'SisStudentInfoSubjDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    subject: null,
                                    subjectType: null,
                                    activeStatus: null,
                                    createDate: null,
                                    createBy: null,
                                    updateDate: null,
                                    updateBy: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('sisStudentInfoSubj', null, { reload: true });
                    }, function() {
                        $state.go('sisStudentInfoSubj');
                    })
                }]
            })
            .state('sisStudentInfoSubj.edit', {
                parent: 'sisStudentInfoSubj',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/sisStudentInfoSubj/sisStudentInfoSubj-dialog.html',
                        controller: 'SisStudentInfoSubjDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['SisStudentInfoSubj', function(SisStudentInfoSubj) {
                                return SisStudentInfoSubj.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('sisStudentInfoSubj', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
