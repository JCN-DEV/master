'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            /*.state('instEmplRecruitInfo', {
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
            })*/
            .state('employeeInfo.recruitmentInfo', {
                parent: 'employeeInfo',
                url: '/recruitment-info',
                data: {
                    authorities: [],
                    pageTitle: 'stepApp.instEmplRecruitInfo.detail.title'
                },
                views: {
                    'employeeView@employeeInfo': {
                        templateUrl: 'scripts/app/entities/employeeInformationSystem/instEmplRecruitInfo/instEmplRecruitInfo-detail.html',
                        controller: 'InstEmplRecruitInfoDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('instEmplRecruitInfo');
                        return $translate.refresh();
                    }]
                }
            })
            .state('employeeInfo.recruitmentInfo.new', {
                parent: 'employeeInfo.recruitmentInfo',
                url: '/new',
                data: {
                    authorities: [],
                },
                views: {
                    'employeeView@employeeInfo': {
                        templateUrl: 'scripts/app/entities/employeeInformationSystem/instEmplRecruitInfo/instEmplRecruitInfo-dialog.html',
                        controller: 'InstEmplRecruitInfoDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('instEmplRecruitInfo');
                        return $translate.refresh();
                    }],
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
                /*onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
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
                }]*/
            })
            .state('employeeInfo.recruitmentInfo.edit', {
                parent: 'employeeInfo.recruitmentInfo',
                url: '/edit',
                data: {
                    authorities: [],
                },
                views: {
                    'employeeView@employeeInfo': {
                        templateUrl: 'scripts/app/entities/employeeInformationSystem/instEmplRecruitInfo/instEmplRecruitInfo-dialog.html',
                        controller: 'InstEmplRecruitInfoDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('instEmplRecruitInfo');
                        return $translate.refresh();
                    }],
                    entity: ['InstEmplRecruitInfoCurrent', function(InstEmplRecruitInfoCurrent) {
                        return InstEmplRecruitInfoCurrent.get({});
                    }]
                }
               /* onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
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
                }]*/
            })
            .state('employeeInfo.recruitmentInfo.delete', {
                parent: 'employeeInfo.recruitmentInfo',
                url: '/{id}/delete',
                data: {
                    authorities: [],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/employeeInformationSystem/instEmplRecruitInfo/instEmplRecruitInfo-delete-dialog.html',
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
