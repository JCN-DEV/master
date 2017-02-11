'use strict';

describe('RisAppFormEduQ Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockRisAppFormEduQ, MockRisNewAppFormTwo;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockRisAppFormEduQ = jasmine.createSpy('MockRisAppFormEduQ');
        MockRisNewAppFormTwo = jasmine.createSpy('MockRisNewAppFormTwo');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'RisAppFormEduQ': MockRisAppFormEduQ,
            'RisNewAppFormTwo': MockRisNewAppFormTwo
        };
        createController = function() {
            $injector.get('$controller')("RisAppFormEduQDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'stepApp:risAppFormEduQUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
