'use strict';

describe('InformationCorrection Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockInformationCorrection, MockInstEmployee, MockInstEmplDesignation;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockInformationCorrection = jasmine.createSpy('MockInformationCorrection');
        MockInstEmployee = jasmine.createSpy('MockInstEmployee');
        MockInstEmplDesignation = jasmine.createSpy('MockInstEmplDesignation');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'InformationCorrection': MockInformationCorrection,
            'InstEmployee': MockInstEmployee,
            'InstEmplDesignation': MockInstEmplDesignation
        };
        createController = function() {
            $injector.get('$controller')("InformationCorrectionDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'stepApp:informationCorrectionUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
