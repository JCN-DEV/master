'use strict';

angular.module('stepApp')
    .factory('IisCurriculumInfoTempSearch', function ($resource) {
        return $resource('api/_search/iisCurriculumInfoTemps/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    }).factory('IisCurriculumInfoTempOfCurrentInstitute', function ($resource) {
        return $resource('api/iisCurriculumInfoTemps/currentInstitute', {}, {
            'query': { method: 'GET', isArray: true}
        });
    }).factory('IisTempCurriculumsOfCurrentInstitute', function ($resource) {
        return $resource('api/iisCurriculumInfoTemps/curriculum/currentInstitute', {}, {
            'query': { method: 'GET', isArray: true}
        });
    }).factory('IisTempCurriculumsByInstituteAndStatus', function ($resource) {
        return $resource('api/iisCurriculumInfoTemps/curriculums/:instituteId/:status', {}, {
            'query': { method: 'GET', isArray: true}
        });
    }).factory('CurriculumsOfCurrentInst', function ($resource) {
        return $resource('api/iisCurriculumInfoTemps/curriculums/currentInstitute', {}, {
            'query': { method: 'GET', isArray: true}
        })
    });
