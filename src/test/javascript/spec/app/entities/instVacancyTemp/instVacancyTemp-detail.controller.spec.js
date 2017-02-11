'use strict';

describe('InstVacancyTemp Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockInstVacancyTemp, MockInstitute, MockCmsTrade, MockCmsSubject, MockInstEmplDesignation;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockInstVacancyTemp = jasmine.createSpy('MockInstVacancyTemp');
        MockInstitute = jasmine.createSpy('MockInstitute');
        MockCmsTrade = jasmine.createSpy('MockCmsTrade');
        MockCmsSubject = jasmine.createSpy('MockCmsSubject');
        MockInstEmplDesignation = jasmine.createSpy('MockInstEmplDesignation');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'InstVacancyTemp': MockInstVacancyTemp,
            'Institute': MockInstitute,
            'CmsTrade': MockCmsTrade,
            'CmsSubject': MockCmsSubject,
            'InstEmplDesignation': MockInstEmplDesignation
        };
        createController = function() {
            $injector.get('$controller')("InstVacancyTempDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'stepApp:instVacancyTempUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
