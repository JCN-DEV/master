'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('risAppFormEduQ', {
                parent: 'entity',
                url: '/risAppFormEduQs',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.risAppFormEduQ.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/risAppFormEduQ/risAppFormEduQs.html',
                        controller: 'RisAppFormEduQController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('risAppFormEduQ');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('risAppFormEduQ.detail', {
                parent: 'entity',
                url: '/risAppFormEduQ/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.risAppFormEduQ.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/risAppFormEduQ/risAppFormEduQ-detail.html',
                        controller: 'RisAppFormEduQDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('risAppFormEduQ');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'RisAppFormEduQ', function($stateParams, RisAppFormEduQ) {
                        return RisAppFormEduQ.get({id : $stateParams.id});
                    }]
                }
            })
            .state('risAppFormEduQ.new', {
                parent: 'risAppFormEduQ',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/risAppFormEduQ/risAppFormEduQ-dialog.html',
                        controller: 'RisAppFormEduQDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    examName: null,
                                    subject: null,
                                    educationalInstitute: null,
                                    passingYear: null,
                                    boardUniversity: null,
                                    additionalInformation: null,
                                    experience: null,
                                    qouta: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('risAppFormEduQ', null, { reload: true });
                    }, function() {
                        $state.go('risAppFormEduQ');
                    })
                }]
            })
            .state('risAppFormEduQ.edit', {
                parent: 'risAppFormEduQ',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/risAppFormEduQ/risAppFormEduQ-dialog.html',
                        controller: 'RisAppFormEduQDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['RisAppFormEduQ', function(RisAppFormEduQ) {
                                return RisAppFormEduQ.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('risAppFormEduQ', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
