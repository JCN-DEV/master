'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('institute', {
                parent: 'entity',
                url: '/institutes',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.institute.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/institute/institutes.html',
                        controller: 'InstituteController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('institute');
                        $translatePartialLoader.addPart('location');
                        $translatePartialLoader.addPart('status');
                        $translatePartialLoader.addPart('instituteType');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('institute.detail', {
                parent: 'entity',
                url: '/institute/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.institute.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/institute/institute-detail.html',
                        controller: 'InstituteDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('institute');
                        $translatePartialLoader.addPart('location');
                        $translatePartialLoader.addPart('status');
                        $translatePartialLoader.addPart('instituteType');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'Institute', function($stateParams, Institute) {
                        return Institute.get({id : $stateParams.id});
                    }]
                }
            })
            .state('institute.new', {
                parent: 'institute',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/institute/institute-dialog.html',
                        controller: 'InstituteDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    name: null,
                                    dateOfEstablishment: null,
                                    mpoCode: null,
                                    postOffice: null,
                                    location: null,
                                    isJoint: null,
                                    multiEducational: null,
                                    firstApprovedEducationalYear: null,
                                    lastApprovedEducationalYear: null,
                                    firstMpoIncludeDate: null,
                                    lastMpoExpireDate: null,
                                    nameOfTradeSubject: null,
                                    lastApprovedSignatureDate: null,
                                    lastCommitteeApprovedDate: null,
                                    lastCommitteeApprovedFile: null,
                                    lastCommitteeApprovedFileContentType: null,
                                    lastCommitteeExpDate: null,
                                    lastCommittee1stMeetingFile: null,
                                    lastCommittee1stMeetingFileContentType: null,
                                    lastCommitteeExpireDate: null,
                                    lastMpoMemorialDate: null,
                                    totalStudent: null,
                                    lengthOfLibrary: null,
                                    widthOfLibrary: null,
                                    numberOfBook: null,
                                    lastMpoIncludeExpireDate: null,
                                    numberOfLab: null,
                                    labArea: null,
                                    code: null,
                                    landPhone: null,
                                    mobile: null,
                                    email: null,
                                    constituencyArea: null,
                                    adminCounselorName: null,
                                    counselorMobileNo: null,
                                    insHeadName: null,
                                    insHeadMobileNo: null,
                                    deoName: null,
                                    deoMobileNo: null,
                                    type: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('institute', null, { reload: true });
                    }, function() {
                        $state.go('institute');
                    })
                }]
            })
            .state('institute.edit', {
                parent: 'institute',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/institute/institute-dialog.html',
                        controller: 'InstituteDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Institute', function(Institute) {
                                return Institute.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('institute', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('institute.delete', {
                parent: 'institute',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/institute/institute-delete-dialog.html',
                        controller: 'InstituteDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['Institute', function(Institute) {
                                return Institute.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('institute', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
