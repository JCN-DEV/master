'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('insAcademicInfoTemp', {
                parent: 'entity',
                url: '/insAcademicInfoTemps',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.insAcademicInfoTemp.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/insAcademicInfoTemp/insAcademicInfoTemps.html',
                        controller: 'InsAcademicInfoTempController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('insAcademicInfoTemp');
                        $translatePartialLoader.addPart('curriculum');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('insAcademicInfoTemp.detail', {
                parent: 'entity',
                url: '/insAcademicInfoTemp/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.insAcademicInfoTemp.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/insAcademicInfoTemp/insAcademicInfoTemp-detail.html',
                        controller: 'InsAcademicInfoTempDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('insAcademicInfoTemp');
                        $translatePartialLoader.addPart('curriculum');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'InsAcademicInfoTemp', function($stateParams, InsAcademicInfoTemp) {
                        return InsAcademicInfoTemp.get({id : $stateParams.id});
                    }]
                }
            })
            .state('insAcademicInfoTemp.new', {
                parent: 'insAcademicInfoTemp',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/insAcademicInfoTemp/insAcademicInfoTemp-dialog.html',
                        controller: 'InsAcademicInfoTempDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    counselorName: null,
                                    curriculum: null,
                                    totalTechTradeNo: null,
                                    tradeTechDetails: null,
                                    status: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('insAcademicInfoTemp', null, { reload: true });
                    }, function() {
                        $state.go('insAcademicInfoTemp');
                    })
                }]
            })
            .state('insAcademicInfoTemp.edit', {
                parent: 'insAcademicInfoTemp',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/insAcademicInfoTemp/insAcademicInfoTemp-dialog.html',
                        controller: 'InsAcademicInfoTempDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['InsAcademicInfoTemp', function(InsAcademicInfoTemp) {
                                return InsAcademicInfoTemp.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('insAcademicInfoTemp', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
