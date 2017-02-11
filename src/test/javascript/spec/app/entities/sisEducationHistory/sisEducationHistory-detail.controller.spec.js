'use strict';

describe('SisEducationHistory Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockSisEducationHistory, MockEduLevel, MockEduBoard;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockSisEducationHistory = jasmine.createSpy('MockSisEducationHistory');
        MockEduLevel = jasmine.createSpy('MockEduLevel');
        MockEduBoard = jasmine.createSpy('MockEduBoard');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'SisEducationHistory': MockSisEducationHistory,
            'EduLevel': MockEduLevel,
            'EduBoard': MockEduBoard
        };
        createController = function() {
            $injector.get('$controller')("SisEducationHistoryDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'stepApp:sisEducationHistoryUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
