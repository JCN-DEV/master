'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('instEmplRecruitInfo', {
                parent: 'entity',
                url: '/instEmplRecruitInfos',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.instEmplRecruitInfo.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/instEmplRecruitInfo/instEmplRecruitInfos.html',
                        controller: 'InstEmplRecruitInfoController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('instEmplRecruitInfo');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('instEmplRecruitInfo.detail', {
                parent: 'entity',
                url: '/instEmplRecruitInfo/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.instEmplRecruitInfo.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/instEmplRecruitInfo/instEmplRecruitInfo-detail.html',
                        controller: 'InstEmplRecruitInfoDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('instEmplRecruitInfo');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'InstEmplRecruitInfo', function($stateParams, InstEmplRecruitInfo) {
                        return InstEmplRecruitInfo.get({id : $stateParams.id});
                    }]
                }
            })
            .state('instEmplRecruitInfo.new', {
                parent: 'instEmplRecruitInfo',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/instEmplRecruitInfo/instEmplRecruitInfo-dialog.html',
                        controller: 'InstEmplRecruitInfoDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    salaryScale: null,
                                    salaryCode: null,
                                    monthlySalaryGovtProvided: null,
                                    monthlySalaryInstituteProvided: null,
                                    gbResolutionReceiveDate: null,
                                    gbResolutionAgendaNo: null,
                                    circularPaperName: null,
                                    circularPublishedDate: null,
                                    recruitExamReceiveDate: null,
                                    dgRepresentativeName: null,
                                    dgRepresentativeDesignation: null,
                                    dgRepresentativeAddress: null,
                                    boardRepresentativeName: null,
                                    boardRepresentativeDesignation: null,
                                    boardRepresentativeAddress: null,
                                    recruitApproveGBResolutionDate: null,
                                    recruitPermitAgendaNo: null,
                                    recruitmentDate: null,
                                    presentInstituteJoinDate: null,
                                    presentPostJoinDate: null,
                                    dgRepresentativeRecordNo: null,
                                    boardRepresentativeRecordNo: null,
                                    department: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('instEmplRecruitInfo', null, { reload: true });
                    }, function() {
                        $state.go('instEmplRecruitInfo');
                    })
                }]
            })
            .state('instEmplRecruitInfo.edit', {
                parent: 'instEmplRecruitInfo',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/instEmplRecruitInfo/instEmplRecruitInfo-dialog.html',
                        controller: 'InstEmplRecruitInfoDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['InstEmplRecruitInfo', function(InstEmplRecruitInfo) {
                                return InstEmplRecruitInfo.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('instEmplRecruitInfo', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('instEmplRecruitInfo.delete', {
                parent: 'instEmplRecruitInfo',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/instEmplRecruitInfo/instEmplRecruitInfo-delete-dialog.html',
                        controller: 'InstEmplRecruitInfoDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['InstEmplRecruitInfo', function(InstEmplRecruitInfo) {
                                return InstEmplRecruitInfo.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('instEmplRecruitInfo', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
