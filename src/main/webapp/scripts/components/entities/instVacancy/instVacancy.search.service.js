'use strict';

angular.module('stepApp')
    .factory('InstVacancySearch', function ($resource) {
        return $resource('api/_search/instVacancys/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    }).factory('InstVacancyCurrentInstForTeacher', function ($resource) {
        return $resource('api/instVacancys/currentInstitute/:tradeId/:subjectId', {}, {
            'get': { method: 'GET', isArray: false}
        });
    }).factory('InstVacancyCurInstForTeacher', function ($resource) {
        return $resource('api/instVacancys/currentInstitute/subjects/:subjectId', {}, {
            'get': { method: 'GET', isArray: false}
        });
    }).factory('InstVacancyForTeacher', function ($resource) {
        return $resource('api/instVacancys/institute/trade/subject/:instId/:tradeId/:subjectId', {}, {
            'get': { method: 'GET', isArray: false}
        });
    }).factory('InstVacancyCurrentInstForStaff', function ($resource) {
        return $resource('api/instVacancys/currentInstitute/designation/:id', {}, {
            'get': { method: 'GET', isArray: false}
        });
    }).factory('InstVacancysCurrentInst', function ($resource) {
        return $resource('api/instVacancys/currentInstitute', {}, {
            'query': { method: 'GET', isArray: true}
        });
    }).factory('InstVacancysByInstitute', function ($resource) {
        return $resource('api/instVacancys/institute/:instituteId', {}, {
            'query': { method: 'GET', isArray: true}
        });
    }).factory('InstVacancyForStaff', function ($resource) {
        return $resource('api/instVacancys/institute/designation/:instId/:desigId', {}, {
            'get': { method: 'GET', isArray: false}
        });
    }).factory('InstVacancySpecialRole', function ($resource) {
        return $resource('api/instVacancys/specialRoles/iisCourse', {}, {
            'apply': { method: 'POST', isArray: false}
        });
    });
